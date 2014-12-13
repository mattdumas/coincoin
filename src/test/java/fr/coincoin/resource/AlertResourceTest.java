package fr.coincoin.resource;

import fr.coincoin.Main;
import fr.coincoin.domain.Alert;
import fr.coincoin.testng.listener.TomcatSuiteListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners(TomcatSuiteListener.class)
public class AlertResourceTest {

    private WebTarget target;


    @BeforeMethod
    public void setUp() throws Exception {
        Client client = ClientBuilder.newClient();
        target = client.target(Main.BASE_URI);
    }


    @Test
    public void should_coincoin() {
        // Given

        // When
        String responseMsg = target.path("api/alerts").request().get(String.class);

        // Then
        assertThat(responseMsg).isEqualTo("Coincoin!");
    }
    
    @Test
    public void should_post_alert() {
        // Given
        Alert alert = new Alert();
        alert.setName("name");
        alert.setUrl("url");
        alert.setEmail("email");
        alert.setFrequency(60);

        Entity<Alert> entity = entity(alert, MediaType.APPLICATION_JSON_TYPE);

        // When
        Response response = target.path("api/alerts").request().post(entity);

        // Then
        Alert content = response.readEntity(Alert.class);

        assertThat(response.getStatus()).isEqualTo(CREATED.getStatusCode());
        assertThat(response.getLocation().toString()).isEqualTo("http://localhost:8080/coincoin/alerts/" + content.getId());

        assertThat(content.getId()).isNotEmpty();
        assertThat(content.getName()).isEqualTo("name");
        assertThat(content.getUrl()).isEqualTo("url");
        assertThat(content.getEmail()).isEqualTo("email");
        assertThat(content.getFrequency()).isEqualTo(60);
    }


}