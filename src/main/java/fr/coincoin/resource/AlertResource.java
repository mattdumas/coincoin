package fr.coincoin.resource;


import fr.coincoin.quartz.AlertScheduler;
import fr.coincoin.domain.Alert;
import org.quartz.SchedulerException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.created;

@Path("alerts")
public class AlertResource {

    private final Map<String, Alert> alerts = new ConcurrentHashMap<>();
    private final AlertScheduler alertScheduler;


    @Inject
    public AlertResource(AlertScheduler alertScheduler) {
        this.alertScheduler = alertScheduler;
    }


    @GET
    @Produces(TEXT_PLAIN)
    public String getIt() {
        return "Coincoin!";
    }


    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response create(Alert alert) throws SchedulerException {
        alert.setId(UUID.randomUUID().toString());
        alerts.putIfAbsent(alert.getId(), alert);

        alertScheduler.scheduleJob(alert);

        URI location = URI.create("/coincoin/alerts/" + alert.getId());

        return created(location).entity(alert).build();
    }


}
