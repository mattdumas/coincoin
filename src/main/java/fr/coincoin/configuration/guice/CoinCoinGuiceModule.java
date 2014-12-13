package fr.coincoin.configuration.guice;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;
import fr.coincoin.AlertScheduler;
import fr.coincoin.job.GuiceJobFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

import javax.inject.Singleton;
import java.io.IOException;

public class CoinCoinGuiceModule extends ServletModule {


    @Override
    protected void configureServlets() {
        bind(AlertScheduler.class).in(Singleton.class);
        bind(JobFactory.class).to(GuiceJobFactory.class).in(Singleton.class);
    }


    @Provides
    public Scheduler scheduler(JobFactory jobFactory) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.setJobFactory(jobFactory);

        scheduler.start();

        return scheduler;
    }

    @Provides
    public Handlebars handlebars() {
        Handlebars handlebars = new Handlebars();
        handlebars.setPrettyPrint(true);
        return handlebars;
    }

    @Provides
    public Template template() throws IOException {
        return handlebars().compile("alerts");
    }


}
