package fr.coincoin.quartz.job;

import fr.coincoin.domain.Ad;
import fr.coincoin.domain.Alert;
import fr.coincoin.parser.AdsParser;
import fr.coincoin.service.MailService;
import org.apache.commons.mail.EmailException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class AlertJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertJob.class);

    private final AdsParser parser;
    private final MailService mailService;


    @Inject
    public AlertJob(AdsParser parser, MailService mailService) {
        this.parser = parser;
        this.mailService = mailService;
    }


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Alert alert = (Alert) context.getMergedJobDataMap().get("alert");

        LOGGER.info("Starting AlertJob for alert {}.", alert);

        try {
            List<Ad> ads = parser.parse(alert.getUrl());
            mailService.sendAds(alert, ads);
        } catch (IOException | EmailException e) {
            throw new JobExecutionException(e);
        }
    }


}
