package fr.coincoin.resource;


import fr.coincoin.domain.Alert;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("alerts")
public class AlertResource {

    private final Map<String, Alert> alerts = new ConcurrentHashMap<>();


    @GET
    @Produces(TEXT_PLAIN)
    public String getIt() {
        return "Coincoin!";
    }


    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response create(Alert alert) {
        alert.setId(UUID.randomUUID().toString());
        alerts.putIfAbsent(alert.getId(), alert);

        URI location = URI.create("/coincoin/alerts/" + alert.getId());
        return Response.created(location).entity(alert).build();
    }


}
