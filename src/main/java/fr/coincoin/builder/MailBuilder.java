package fr.coincoin.builder;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import fr.coincoin.domain.Ad;

import java.io.IOException;
import java.util.List;

public class MailBuilder {

    private final Template template;

    public MailBuilder() {
        Handlebars handlebars = new Handlebars();
        handlebars.setPrettyPrint(true);

        try {
            template = handlebars.compile("alerts");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String build(List<Ad> ads) throws IOException {
        return template.apply(ads);
    }


}
