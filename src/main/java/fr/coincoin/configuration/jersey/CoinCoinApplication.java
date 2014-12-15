package fr.coincoin.configuration.jersey;

import fr.coincoin.configuration.guice.GuiceInjectorServletContextListener;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;

/**
 * Not an implementation I'm satisfy of.
 * See https://java.net/jira/browse/JERSEY-1950 & https://java.net/jira/browse/HK2-121
 */
public class CoinCoinApplication extends ResourceConfig {


    @Inject
    public CoinCoinApplication(ServiceLocator serviceLocator) {
        packages("fr.coincoin");

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);

        GuiceIntoHK2Bridge bridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);

        bridge.bridgeGuiceInjector(GuiceInjectorServletContextListener.injector);
    }


}
