package fr.coincoin.builder;

import com.github.jknack.handlebars.Template;
import fr.coincoin.domain.Ad;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class MailBuilder {

    private final Template template;

    @Inject
    public MailBuilder(Template template) {
        this.template = template;
    }


    public String build(List<Ad> ads) throws IOException {
        return template.apply(ads);
    }


}
