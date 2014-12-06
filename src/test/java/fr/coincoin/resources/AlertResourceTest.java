package fr.coincoin.resources;

import fr.coincoin.testng.listener.GrizzlySuiteListener;
import fr.coincoin.Main;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.assertj.core.api.Assertions.assertThat;

@Listeners({GrizzlySuiteListener.class})
public class AlertResourceTest {

    private WebTarget target;

    @BeforeMethod
    public void setUp() throws Exception {
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @Test
    public void should_coincoin() {
        String responseMsg = target.path("alerts").request().get(String.class);
        assertThat(responseMsg).isEqualTo("Coincoin!");
    }

}