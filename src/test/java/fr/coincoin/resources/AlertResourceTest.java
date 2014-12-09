package fr.coincoin.resources;

import fr.coincoin.Main;
import fr.coincoin.domain.Alert;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
import static org.assertj.core.api.Assertions.contentOf;

public class AlertResourceTest {

    private HttpServer server;
    private WebTarget target;

    @BeforeMethod
    public void setUp() throws Exception {
        server = Main.startServer();

        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void should_coincoin() {
        String responseMsg = target.path("alerts").request().get(String.class);
        assertThat(responseMsg).isEqualTo("Coincoin!");
    }
    
    @Test
    public void should_post_alert() {
        Alert alert = new Alert();
        alert.setName("name");
        alert.setUrl("url");
        alert.setEmail("email");
        alert.setFrequency(60);

        Entity<Alert> entity = entity(alert, MediaType.APPLICATION_JSON_TYPE);

        Response response = target.path("alerts").request().post(entity);

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