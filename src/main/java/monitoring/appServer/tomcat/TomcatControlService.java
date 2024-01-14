package monitoring.appServer.tomcat;

import lombok.SneakyThrows;
import monitoring.appServer.common.ResourceContext;
import monitoring.appServer.common.State;
import monitoring.commands.control.ControlStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class TomcatControlService implements ControlStrategy {


    private static final Logger logger = LoggerFactory.getLogger(TomcatControlService.class);

    @Autowired
    TomcatService tomcatService;


    /**
     * Attempts to stop a Tomcat server resource.
     *
     * This method executes a bash command to stop the Tomcat server.
     * It waits for the command to complete and checks the state of the Tomcat server in intervals.
     * If the server stops successfully, the state 'STOPPED' is returned.
     * If the server does not stop, the current state of the server is returned.
     * In case of any IOException or InterruptedException during the process execution, the method logs the
     * error and returns the state 'UNKNOWN'.
     *
     * @param resourceContext The context of the resource being stopped.
     * @return State The state of the Tomcat server after attempting to stop it.
     */
    @SneakyThrows
    @Override
    public State stopResource(ResourceContext resourceContext) {
        String[] command = {"bash", "-c", "sudo /opt/tomcat/apache-tomcat-10.1.17/bin/shutdown.sh"};
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            logger.info("creating process to stop tomcat");
        }
        catch (IOException | InterruptedException e) {
            logger.error("Error executing command or waiting for completion", e);
            return State.UNKNOWN;
        }


        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if (tomcatService.getTomcatState() == State.STOPPED) {
                logger.info("Successfully stopped tomcat");
                return State.STOPPED;
            }
            Thread.sleep(1000);
        }

        logger.info("Tomcat state is {}", tomcatService.getTomcatState());
        return  tomcatService.getTomcatState();
    }



    /**
     * Attempts to start a Tomcat server resource.
     *
     * This method executes a bash command to start the Tomcat server.
     * It waits for the command to complete and checks the state of the Tomcat server in intervals.
     * If the server starts successfully, the state 'RUNNING' is returned.
     * If the server does not start, the current state of the server is returned.
     * In case of any IOException or InterruptedException during the process execution, the method logs the
     * error and returns the state 'UNKNOWN'.
     *
     * @param resourceContext The context of the resource being stopped.
     * @return State The state of the Tomcat server after attempting to stop it.
     */
    @SneakyThrows
    @Override
    public State startResource(ResourceContext resourceContext) {
        String[] command = {"bash", "-c", "sudo /opt/tomcat/apache-tomcat-10.1.17/bin/startup.sh"};
        try {
            Process process = Runtime.getRuntime().exec(command);
            logger.info("creating process to start tomcat");
            process.waitFor();
        }
        catch (IOException | InterruptedException e) {
            logger.error("Error executing command or waiting for completion", e);
            return State.UNKNOWN;
        }


        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if (tomcatService.getTomcatState() == State.RUNNING) {
                logger.info("Tomcat is running");
                return State.RUNNING;
            }
            Thread.sleep(1000);
        }
        return  tomcatService.getTomcatState();
    }
}
