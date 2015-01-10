package fr.coincoin.parser;

import fr.coincoin.domain.Ad;
import fr.coincoin.test.FileUtils;
import fr.coincoin.testng.listener.MockServerSuiteListener;
import org.mockserver.client.server.MockServerClient;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


@Listeners(MockServerSuiteListener.class)
public class AdsParserTest {

    private AdsParser parser = new AdsParser();


    @Test
    public void should_parse() throws IOException {
        // Given
        givenLBCExpectations();

        String url = "http://localhost:8888/mock_lbc";

        // When
        List<Ad> ads = parser.parse(url);

        // Then
        assertThat(ads).hasSize(35);

        assertThat(ads.get(0).getName()).isEqualTo("Maison d'habitation Type T8");
        assertThat(ads.get(0).getUrl()).isEqualTo("http://www.leboncoin.fr/ventes_immobilieres/663501367.htm?ca=5_s");
        assertThat(ads.get(0).getImageUrl()).isEqualTo("http://193.164.197.50/thumbs/92a/92aad476f7b31cc2aba4c8be9424aaa3f7ba6b6c.jpg");
        assertThat(ads.get(0).getPrice()).isEqualTo("105 000 €");

        assertThat(ads.get(1).getName()).isEqualTo("Maison avec terrain");
        assertThat(ads.get(1).getUrl()).isEqualTo("http://www.leboncoin.fr/ventes_immobilieres/744562553.htm?ca=5_s");
        assertThat(ads.get(1).getImageUrl()).isEqualTo("http://193.164.196.30/thumbs/20d/20d0af8e216bd2ffef48218398918ee5e419e377.jpg");
        assertThat(ads.get(1).getPrice()).isEqualTo("165 000 €");

        assertThat(ads.get(2).getName()).isEqualTo("Ancienne ferme");
        assertThat(ads.get(2).getUrl()).isEqualTo("http://www.leboncoin.fr/ventes_immobilieres/732487763.htm?ca=5_s");
        assertThat(ads.get(2).getImageUrl()).isEqualTo("http://193.164.197.60/thumbs/772/77201509e1b6dc708e5c8a79b3fa03b12e26c5a7.jpg");
        assertThat(ads.get(2).getPrice()).isEqualTo("175 000 €");
    }

    @Test
    public void should_parse_when_image_or_price_are_missing() throws IOException {
        // Given
        givenLBCExpectations();

        // When
        List<Ad> ads = parser.parse("http://localhost:8888/lbc_sample_missing_information");

        // Then
        assertThat(ads).hasSize(35);

        assertThat(ads.get(13).getName()).isEqualTo("Immeuble locatif avec 3 appartements");
        assertThat(ads.get(13).getUrl()).isEqualTo("http://www.leboncoin.fr/ventes_immobilieres/745190855.htm?ca=12_s");
        assertThat(ads.get(13).getImageUrl()).isEmpty();
        assertThat(ads.get(13).getPrice()).isEqualTo("117 500 €");

        assertThat(ads.get(14).getName()).isEqualTo("Maison 5 pieces");
        assertThat(ads.get(14).getUrl()).isEqualTo("http://www.leboncoin.fr/ventes_immobilieres/744597905.htm?ca=12_s");
        assertThat(ads.get(14).getImageUrl()).isEmpty();
        assertThat(ads.get(14).getPrice()).isEqualTo("92 600 €");

        assertThat(ads.get(15).getName()).isEqualTo("Terrain à bâtir rue varmancourt, BLANZY");
        assertThat(ads.get(15).getUrl()).isEqualTo("http://www.leboncoin.fr/ventes_immobilieres/672007033.htm?ca=12_s");
        assertThat(ads.get(15).getImageUrl()).isEqualTo("http://193.164.196.50/thumbs/341/34175001130d5fa6b91bbff42b1a78e22eba6a8d.jpg");
        assertThat(ads.get(15).getPrice()).isEmpty();
    }

    private static void givenLBCExpectations() {
        new MockServerClient("localhost", 8888)
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/mock_lbc")
            )
            .respond(
                    response()
                            .withStatusCode(200)
                            .withBody(FileUtils.toString(AdsParserTest.class.getResourceAsStream("/lbc_sample.html")))
            );

        new MockServerClient("localhost", 8888)
            .when(
                    request()
                            .withMethod("GET")
                            .withPath("/lbc_sample_missing_information")
            )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(FileUtils.toString(AdsParserTest.class.getResourceAsStream("/lbc_sample_missing_information.html")))
                );
    }
}