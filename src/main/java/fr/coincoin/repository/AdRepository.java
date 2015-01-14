package fr.coincoin.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.coincoin.domain.Ads;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;

import javax.inject.Inject;
import java.io.IOException;

public class AdRepository {

    private final Client client;
    private final ObjectMapper objectMapper;

    @Inject
    public AdRepository(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }


    public void save(String alertId, Ads ads) {
        try {
            String rawValue = objectMapper.writeValueAsString(ads);
            client.prepareIndex("coincoin", "ad", alertId).setSource(rawValue).execute().actionGet();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public Ads find(String alertId) {
        GetResponse response = client.prepareGet("coincoin", "ad", alertId).execute().actionGet();

        try {
            return objectMapper.readValue(response.getSourceAsBytes(), Ads.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


}
