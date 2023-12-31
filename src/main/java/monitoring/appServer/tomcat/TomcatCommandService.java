package monitoring.appServer.tomcat;

import monitoring.appServer.common.State;
import monitoring.commands.CommandExec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class TomcatCommandService {

    @Autowired
    CommandExec commandExec;

    private static final Logger logger = LoggerFactory.getLogger(TomcatCommandService.class);

    public boolean isTomcatRunning() {
        /*
        Checks if Tomcat is running by checking if Tomcat process is present and returns a boolean value
        */

        String[] command = {"bash", "-c", "ps aux | grep [c]atalina.startup.Bootstrap"}; // command that determines presence of Tomcat process
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            boolean isTomcatFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("catalina")) {
                    logger.info("Tomcat process found: {}", line);
                    isTomcatFound = true;
                    break;
                }
            }
            if (isTomcatFound) {
                logger.info("Tomcat is running.");
                return true;
            } else {
                logger.info("Tomcat is not running.");
                return false;
            }
        } catch (IOException e) {
            logger.error("Encountered exception during isTomcatRunning", e);
            return false;
        } finally {
            if (process != null) {
                process.destroy();
                logger.info("Destroyed checking process.");
            }
        }
    }

    public State changeTomcatState(String[] command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            logger.error("Error executing command or waiting for completion", e);
            return State.UNKNOWN;
        }

        boolean isStopCommand = command[2].contains("stop") || command[2].contains("shutdown");
        boolean isStartCommand = command[2].contains("start");

        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if (isStopCommand && !isTomcatRunning()) {
                logger.info("Tomcat has stopped.");
                return State.STOPPED;
            } else if (isStartCommand && isTomcatRunning()) {
                logger.info("Tomcat is running.");
                return State.RUNNING;
            }
            else logger.info("Finding it difficult to change tomcat state.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Interrupted while waiting to recheck Tomcat state", e);
            }
        }

        State finalState = isStopCommand ? (isTomcatRunning() ? State.RUNNING : State.STOPPED)
                : (isStartCommand ? (isTomcatRunning() ? State.RUNNING : State.STOPPED)
                : State.UNKNOWN);
        logger.info("Final determined state of Tomcat: {}", finalState);
        return finalState;
    }





    }



