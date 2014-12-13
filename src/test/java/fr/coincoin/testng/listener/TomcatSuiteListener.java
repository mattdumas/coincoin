package fr.coincoin.testng.listener;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import javax.servlet.ServletException;
import java.io.File;

import static org.apache.catalina.LifecycleState.DESTROYED;
import static org.apache.catalina.LifecycleState.STOPPED;

public class TomcatSuiteListener implements ISuiteListener {

    private Tomcat tomcat;


    @Override
    public void onStart(ISuite suite) {
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setBaseDir("build"); // put Tomcat temps files into gradle build directory

        try {
            tomcat.addWebapp("/coincoin", new File("src/main/webapp").getAbsolutePath());
            tomcat.start();
        } catch (LifecycleException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            if (tomcat.getServer() != null && tomcat.getServer().getState() != DESTROYED) {
                if (tomcat.getServer().getState() != STOPPED) {
                    tomcat.stop();
                }

                tomcat.destroy();
            }
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }


}
