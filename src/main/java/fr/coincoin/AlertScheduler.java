package fr.coincoin;

import fr.coincoin.domain.Alert;
import fr.coincoin.job.AlertJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertScheduler {

    private Scheduler scheduler;

    public AlertScheduler() {
        initScheduler();
    }


    public void scheduleJob(Alert alert) throws SchedulerException {
        JobDetail job = newJob(AlertJob.class).withIdentity("job-" + alert.getId(), "alertGroup").build();

        Trigger trigger = newTrigger().withIdentity("trigger-" + alert.getId(), "alertGroup")
                                      .startNow()
                                      .withSchedule(simpleSchedule().withIntervalInSeconds(alert.getFrequency())
                                              .repeatForever())
                                      .usingJobData("name", alert.getName())
                                      .usingJobData("email", alert.getEmail())
                                      .usingJobData("url", alert.getUrl())
                                      .build();

        scheduler.scheduleJob(job, trigger);
    }


    private void initScheduler() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


}
