package fr.coincoin.Builder;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import fr.coincoin.domain.Ad;

import java.io.IOException;
import java.util.List;

public class MailBuilder {

    private final Template template;

    public MailBuilder() {
        Handlebars handlebars = new Handlebars();
        try {
            template = handlebars.compile("alerts");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateEmail(List<Ad> ads) throws IOException {
        return template.apply(ads);
    }

}
