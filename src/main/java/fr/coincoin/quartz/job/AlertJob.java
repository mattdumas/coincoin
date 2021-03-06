package fr.coincoin.quartz.job;

import fr.coincoin.domain.Ad;
import fr.coincoin.domain.Ads;
import fr.coincoin.domain.Alert;
import fr.coincoin.parser.AdsParser;
import fr.coincoin.repository.AdRepository;
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

    private final AdRepository adRepository;


    @Inject
    public AlertJob(AdsParser parser, MailService mailService, AdRepository adRepository) {
        this.parser = parser;
        this.mailService = mailService;
        this.adRepository = adRepository;
    }


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Alert alert = (Alert) context.getMergedJobDataMap().get("alert");

        LOGGER.info("Starting AlertJob for alert {}.", alert);

        try {
            List<Ad> ads = parser.parse(alert.getUrl());

            Ads wrapper = new Ads();
            wrapper.setAds(ads);

            adRepository.save(alert.getId(), wrapper);

            mailService.sendAds(alert, ads);
        }
        catch (IOException | EmailException e) {
            throw new JobExecutionException(e);
        }
    }


}
