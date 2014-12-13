package fr.coincoin.builder;

import fr.coincoin.configuration.guice.CoinCoinGuiceModule;
import fr.coincoin.domain.Ad;
import fr.coincoin.test.FileUtils;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Guice(modules = CoinCoinGuiceModule.class)
public class MailBuilderTest {

    @Inject
    private MailBuilder mailBuilder;

    @Test
    public void should_build() throws IOException {
        // Given
        List<Ad> ads = new ArrayList<>();

        Ad ad1 = new Ad.Builder().withName("Maison d'habitation Type T8")
                                 .withUrl("http://www.leboncoin.fr/ventes_immobilieres/663501367.htm?ca=5_s")
                                 .withImageUrl("http://193.164.197.50/thumbs/92a/92aad476f7b31cc2aba4c8be9424aaa3f7ba6b6c.jpg")
                                 .withPrice("105 000 €")
                                 .build();

        Ad ad2 = new Ad.Builder().withName("Maison avec terrain")
                                 .withUrl("http://www.leboncoin.fr/ventes_immobilieres/744562553.htm?ca=5_s")
                                 .withImageUrl("http://193.164.196.30/thumbs/20d/20d0af8e216bd2ffef48218398918ee5e419e377.jpg")
                                 .withPrice("165 000 €")
                                 .build();

        ads.add(ad1);
        ads.add(ad2);

        // When
        String result = mailBuilder.build(ads);

        // Then
        assertThat(result).isEqualTo(FileUtils.toString(getClass().getResourceAsStream("/alerts.html")));
    }



}