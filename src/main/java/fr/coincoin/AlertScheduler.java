package fr.coincoin;

import fr.coincoin.domain.Alert;
import fr.coincoin.job.AlertJob;
import org.quartz.*;

import javax.inject.Inject;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertScheduler {

    private final Scheduler scheduler;


    @Inject
    public AlertScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    public void scheduleJob(Alert alert) throws SchedulerException {
        JobDetail job = newJob(AlertJob.class).withIdentity("job-" + alert.getId(), "alertGroup").build();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("alert", alert);

        Trigger trigger = newTrigger().withIdentity("trigger-" + alert.getId(), "alertGroup")
                                      .startNow()
                                      .withSchedule(simpleSchedule().withIntervalInMinutes(alert.getFrequency()).repeatForever())
                                      .usingJobData(jobDataMap)
                                      .build();

        scheduler.scheduleJob(job, trigger);
    }


}
