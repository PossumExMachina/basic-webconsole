package monitoring.docker;

import lombok.SneakyThrows;
import monitoring.appServer.common.ResourceContext;
import monitoring.appServer.common.State;
import monitoring.commands.control.ControlStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class DockerControlService implements ControlStrategy {

    @Autowired
    private ContainerRepository containerRepository;

    private static final Logger logger = LoggerFactory.getLogger(DockerControlService.class);


    @SneakyThrows
    @Override
    public State stopResource(ResourceContext resourceContext) {
        DockerContainer container = containerRepository.getContainerByID(resourceContext.getResourceId());
        String[] command = {"sudo", "docker", "stop", container.getContainerID()};

        logger.info("STOPPING :" + container);
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
            if (container.getState() == State.STOPPED) {
                return State.STOPPED;
            }
            Thread.sleep(1000);
        }

        return  container.getState();
    }


    @SneakyThrows
    @Override
    public State startResource(ResourceContext resourceContext) {
        DockerContainer container = containerRepository.getContainerByID(resourceContext.getResourceId());
        logger.info("Starting docker");
        String[] command = {"sudo", "docker", "start", container.getContainerID()};
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        }
        catch (IOException | InterruptedException e) {
            logger.error("Error starting dockeror waiting for completion", e);
            return State.UNKNOWN;
        }


        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if (container.getState() == State.RUNNING) {
                return State.RUNNING;
            }
            Thread.sleep(1000);
        }

        return  container.getState();
    }
}
