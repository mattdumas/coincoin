package fr.coincoin.job;

import fr.coincoin.domain.Ad;
import fr.coincoin.parser.AdsParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.util.List;

public class AlertJob implements Job {

    private String name;
    private String email;
    private String url;

    private AdsParser parser = new AdsParser();


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Akheu coincoin");

        try {
            List<Ad> ads = parser.parse(url);

        } catch (IOException e) {
            throw new JobExecutionException(e);
        }
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
