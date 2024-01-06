package monitoring.docker;

import lombok.SneakyThrows;
import monitoring.appServer.common.State;
import monitoring.commands.control.ControlStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class DockerControlService implements ControlStrategy {

    private DockerContainer dockerContainer;

    private static final Logger logger = LoggerFactory.getLogger(DockerControlService.class);

    @SneakyThrows
    public State stopResource() {
        String[] command = {"docker stop ", dockerContainer.getName()};
        try {
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
            }
        catch (IOException | InterruptedException e) {
                logger.error("Error executing command or waiting for completion", e);
                return State.UNKNOWN;
            }


        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if (dockerContainer.getState() == State.STOPPED) {
                return State.STOPPED;
            }
            Thread.sleep(1000);
        }

        return  dockerContainer.getState();
    }


    @SneakyThrows
 //   @Override
    public State startResource() {
        String[] command = {"docker start ", dockerContainer.getName()};
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        }
        catch (IOException | InterruptedException e) {
            logger.error("Error executing command or waiting for completion", e);
            return State.UNKNOWN;
        }


        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if (dockerContainer.getState() == State.RUNNING) {
                return State.RUNNING;
            }
            Thread.sleep(1000);
        }

        return  dockerContainer.getState();
    }
}
