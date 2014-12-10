package fr.coincoin.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AlertJob implements Job {

    private String name;
    private String email;
    private String url;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Akheu coincoin");
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
