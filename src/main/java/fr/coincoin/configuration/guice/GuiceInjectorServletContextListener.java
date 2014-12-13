package fr.coincoin.configuration.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceInjectorServletContextListener extends GuiceServletContextListener {

    public static Injector injector;


    @Override
    protected Injector getInjector() {
        injector = Guice.createInjector(new CoinCoinGuiceModule());
        return injector;
    }


}
