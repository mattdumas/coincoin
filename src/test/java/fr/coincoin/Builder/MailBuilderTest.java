package fr.coincoin.Builder;

import fr.coincoin.domain.Ad;
import fr.coincoin.test.FileUtils;
import org.assertj.core.util.Files;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class MailBuilderTest {

    private MailBuilder mailBuilder = new MailBuilder();

    @Test
    public void should_compile() throws IOException {

        // Given
        ArrayList<Ad> ads = new ArrayList<>();

        Ad ad1 = new Ad.Builder()
                .withName("Mon annonce")
                .withUrl("http://localhost")
                .withPrice("a price")
                .withImageUrl("http://localhost/image").build();

        Ad ad2 = new Ad.Builder()
                .withName("Mon annonce 2")
                .withUrl("http://localhost2")
                .withPrice("a price 2")
                .withImageUrl("http://localhost2/image").build();

        ads.add(ad1);
        ads.add(ad2);

        // When
        String result = mailBuilder.generateEmail(ads);

        // Then
        assertThat(result).isEqualTo(FileUtils.toString(getClass().getResourceAsStream("/alerts.html")));
    }

}