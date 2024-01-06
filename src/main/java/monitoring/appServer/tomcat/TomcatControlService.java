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
