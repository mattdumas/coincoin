package fr.coincoin.service;

import fr.coincoin.builder.MailBuilder;
import fr.coincoin.domain.Ad;
import fr.coincoin.domain.Alert;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.IOException;
import java.util.List;

public class MailService {

    private final MailBuilder mailBuilder;


    public MailService(MailBuilder mailBuilder) {
        this.mailBuilder = mailBuilder;
    }


    public void sendAds(Alert alert, List<Ad>ads) throws IOException, EmailException {
        String content = mailBuilder.build(ads);

        Email email = new HtmlEmail();

        email.setHostName("localhost");
        email.setSmtpPort(3025);
        email.setFrom("no-reply@coicoin.fr");
        email.addTo(alert.getEmail());
        email.setSubject("Votre alerte CoinCoin : " + alert.getName());
        email.setMsg(content);

        email.send();
    }


}
