package fr.coincoin.resource;


import fr.coincoin.domain.Alert;
import fr.coincoin.job.AlertJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Path("alerts")
public class AlertResource {

    private final Map<String, Alert> alerts = new ConcurrentHashMap<>();
    private Scheduler scheduler;


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

        this.scheduler = StdSchedulerFactory.getDefaultScheduler();

        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(AlertJob.class)
                .withIdentity("job1", "group1")
                .build();

// Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(alert.getFrequency())
                        .repeatForever())
                .build();

// Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);

        URI location = URI.create("/coincoin/alerts/" + alert.getId());
        return Response.created(location).entity(alert).build();


    }


}
