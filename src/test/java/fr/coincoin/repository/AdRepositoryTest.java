package fr.coincoin.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.coincoin.domain.Ad;
import fr.coincoin.domain.Ads;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class AdRepositoryTest {

    Node node;

    @BeforeTest
    public void init_elastic_search() {
        node = nodeBuilder().local(true).node();
    }

    @AfterTest
    public void close_elastic_search() {
        node.close();
    }

    @Test
    public void should_save_ads() throws IOException {
        // Given
        Client client = node.client();

        ObjectMapper mapper = new ObjectMapper();

        List<Ad> ads = new ArrayList<>();
        ads.add(new Ad.Builder().withName("ad1").withUrl("url1").build());
        ads.add(new Ad.Builder().withName("ad2").withUrl("url2").build());

        Ads ads2 = new Ads();
        ads2.setAds(ads);

        // When
        new AdRepository(client, mapper).save("1234", ads2);

        GetResponse response = client.prepareGet("coincoin", "ad", "1234").execute().actionGet();

        Ads results = new ObjectMapper().readValue(response.getSourceAsString(), Ads.class);

        // Then
        assertThat(results.getAds()).hasSize(2);
    }

    @Test
    public void should_return_ads() {
        // Given

        Client client = node.client();

        ObjectMapper mapper = new ObjectMapper();

        List<Ad> ads = new ArrayList<>();
        ads.add(new Ad.Builder().withName("ad1").withUrl("url1").build());
        ads.add(new Ad.Builder().withName("ad2").withUrl("url2").build());

        Ads ads1 = new Ads();
        ads1.setAds(ads);

        AdRepository adRepository = new AdRepository(client, mapper);
        adRepository.save("1234", ads1);

        // When
        Ads result = adRepository.find("1234");

        // Then
        assertThat(result.getAds().size()).isEqualTo(2);
    }


}