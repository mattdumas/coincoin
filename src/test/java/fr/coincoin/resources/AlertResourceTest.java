package fr.coincoin.resources;

import fr.coincoin.Main;
import fr.coincoin.domain.Alert;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.glassfish.grizzly.http.server.HttpServer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

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
    
    @Test(enabled = false)
    public void should_post_alert() {
        Response response = target.path("alerts").request().post(Entity.entity(new Alert(), MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getEntity()).isNotNull();
    }

}