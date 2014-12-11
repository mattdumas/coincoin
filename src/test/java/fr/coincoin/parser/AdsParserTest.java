package fr.coincoin.parser;

import fr.coincoin.domain.Ad;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdsParserTest {

    private AdsParser parser = new AdsParser();

    @Test
    public void should_parse() throws IOException {
        // Given
        String url = "http://www.leboncoin.fr/ventes_immobilieres/offres/bourgogne/?f=a&th=1&ret=1";

        // When
        List<Ad> ads = parser.parse(url);

        // Then
        assertThat(ads).isNotNull();
        assertThat(ads.get(0).getName()).isNotEmpty();
        assertThat(ads.get(0).getUrl()).isNotEmpty();
        assertThat(ads.get(0).getImageUrl()).isNotEmpty();
        assertThat(ads.get(0).getPrice()).isNotEmpty();
    }

}