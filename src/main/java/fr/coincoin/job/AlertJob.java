package fr.coincoin.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AlertJob implements Job {
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println("Akheu coincoin");
  }
}
