package fr.coincoin.testng.listener;

import fr.coincoin.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/**
 * Listener used to start and stop Grizzly server once per suite
 */
public class GrizzlySuiteListener implements ISuiteListener {

    private HttpServer server;

    @Override
    public void onStart(ISuite suite) {
        server = Main.startServer();
    }

    @Override
    public void onFinish(ISuite suite) {
        server.shutdownNow();
    }
}
