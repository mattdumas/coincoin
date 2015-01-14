package fr.coincoin.configuration.guice;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import fr.coincoin.configuration.CoinCoinPropertiesConfiguration;
import fr.coincoin.quartz.AlertScheduler;
import fr.coincoin.quartz.job.GuiceJobFactory;
import org.apache.commons.configuration.Configuration;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Properties;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class CoinCoinGuiceModule extends ServletModule {


    @Override
    protected void configureServlets() {
        CoinCoinPropertiesConfiguration propertiesConfiguration = new CoinCoinPropertiesConfiguration();
        Configuration configuration = propertiesConfiguration.getConfiguration();

        // Bind properties to be able to access them with @Named("propertyKey")
        Properties properties = propertiesConfiguration.getProperties(configuration);
        Names.bindProperties(binder(), properties);

        bind(AlertScheduler.class).in(Singleton.class);
        bind(JobFactory.class).to(GuiceJobFactory.class).in(Singleton.class);
        bind(Configuration.class).toInstance(configuration);
    }


    @Provides @Singleton
    public Scheduler scheduler(JobFactory jobFactory) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.setJobFactory(jobFactory);

        scheduler.start();

        return scheduler;
    }

    @Provides @Singleton
    public Handlebars handlebars() {
        Handlebars handlebars = new Handlebars();
        handlebars.setPrettyPrint(true);
        return handlebars;
    }

    @Provides @Singleton
    public Template template() throws IOException {
        return handlebars().compile("templates/alerts");
    }

    @Provides @Singleton
    public Node node() {
        return nodeBuilder().client(true).node();
    }

    @Provides @Singleton
    public Client client(Node node) {
        return node.client();
    }


}
