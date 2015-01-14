package fr.coincoin.configuration.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import org.elasticsearch.node.Node;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.servlet.ServletContextEvent;

public class GuiceInjectorServletContextListener extends GuiceServletContextListener {

    public static Injector injector;


    @Override
    protected Injector getInjector() {
        injector = Guice.createInjector(Stage.PRODUCTION, new CoinCoinGuiceModule());
        return injector;
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);

        Scheduler scheduler = injector.getInstance(Scheduler.class);
        try {
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        Node node = injector.getInstance(Node.class);
        node.close();
    }
}
