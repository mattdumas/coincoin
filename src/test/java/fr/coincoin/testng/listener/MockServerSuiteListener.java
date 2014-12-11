package fr.coincoin.testng.listener;

import org.mockserver.integration.ClientAndServer;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class MockServerSuiteListener implements ISuiteListener {

    private ClientAndServer mockServer;


    @Override
    public void onStart(ISuite suite) {
        mockServer = startClientAndServer(8888);
    }

    @Override
    public void onFinish(ISuite suite) {
        mockServer.stop();
    }


}
