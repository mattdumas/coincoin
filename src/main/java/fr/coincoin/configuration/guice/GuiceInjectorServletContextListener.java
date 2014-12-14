package fr.coincoin.configuration.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.servlet.ServletContextEvent;

public class GuiceInjectorServletContextListener extends GuiceServletContextListener {

    public static Injector injector;


    @Override
    protected Injector getInjector() {
        injector = Guice.createInjector(new CoinCoinGuiceModule());
        return injector;
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);

        Scheduler scheduler = injector.getInstance(Scheduler.class);
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
