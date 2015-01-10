package fr.coincoin.service;

import com.icegreen.greenmail.util.GreenMail;
import fr.coincoin.builder.MailBuilder;
import fr.coincoin.domain.Ad;
import fr.coincoin.domain.Alert;
import org.assertj.core.api.SoftAssertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.internet.MimeMessage;
import java.util.List;

import static com.icegreen.greenmail.util.GreenMailUtil.getBody;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class MailServiceTest {

    private GreenMail greenMail;

    @Mock
    private MailBuilder mailBuilder;

    @InjectMocks
    private MailService mailService;


    @BeforeMethod
    public void setUp() {
        greenMail = new GreenMail(); // uses test ports by default
        greenMail.start();

        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void tearDown() {
        greenMail.stop();
    }


    @Test
    public void should_send_email() throws Exception {
        // Given
        Alert alert = new Alert();
        alert.setName("Ma maison bourguignonne");
        alert.setEmail("foo@bar.com");

        Ad ad1 = new Ad.Builder().build();
        Ad ad2 = new Ad.Builder().build();
        List<Ad> ads = asList(ad1, ad2);

        when(mailBuilder.build(ads)).thenReturn("whatever");
        greenMail.reset();

        // When
        mailService.sendAds(alert, ads);

        // Then
        assertThat(greenMail.waitForIncomingEmail(1)).isTrue();

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(receivedMessages).hasSize(1);
        softly.assertThat(receivedMessages[0].getFrom()[0].toString()).isEqualTo("no-reply@coicoin.fr");
        softly.assertThat(receivedMessages[0].getAllRecipients()[0].toString()).isEqualTo("foo@bar.com");
        softly.assertThat(receivedMessages[0].getSubject()).isEqualTo("Votre alerte CoinCoin : Ma maison bourguignonne");
        softly.assertThat(getBody(receivedMessages[0])).contains("whatever");
        softly.assertAll();
    }


}