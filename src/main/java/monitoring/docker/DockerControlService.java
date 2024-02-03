package monitoring.docker;

import lombok.SneakyThrows;
import monitoring.common.ResourceContext;
import monitoring.commands.control.ControlStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class DockerControlService implements ControlStrategy {

    @Autowired
    private ContainerRepository containerRepository;

    private static final Logger logger = LoggerFactory.getLogger(DockerControlService.class);


    /**
     * Attempts to stop a Docker container based on the provided ResourceContext.
     * <p>
     * Retrieves the DockerContainer by its ID and executes a command to stop it. It checks the container's state
     * up to a specified number of attempts to confirm it has stopped. Returns the container's final state.
     *
     * @param resourceContext Context of the resource to be stopped, containing the resource ID.
     * @return State The final state of the Docker container, STOPPED if successfully stopped, otherwise its current state.
     */
    @SneakyThrows
    @Override
    public String stopResource(ResourceContext resourceContext) {
        DockerContainer container = containerRepository.getContainerByID(resourceContext.getResourceId());
        String[] command = {"sudo", "docker", "stop", container.getID()};

        logger.info("STOPPING :" + container);
        try {
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
                logger.info("Waiting for the process to finish");
            }
        catch (IOException | InterruptedException e) {
                logger.error("Error executing command or waiting for completion", e);
                return "UNKNOWN";
            }

        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if (Objects.equals(container.getState(), "STOPPED")) {

                return "State.STOPPED";
            }
            Thread.sleep(1000);
        }
        logger.info("returning state of container: {}", container.getState());
        return  container.getState();
    }


    /**
     * Attempts to start a Docker container based on the provided ResourceContext.
     * <p>
     * Retrieves the DockerContainer by its ID and executes a command to start it. It checks the container's state
     * up to a specified number of attempts to confirm it has started. Returns the container's final state.
     *
     * @param resourceContext Context of the resource to be started, containing the resource ID.
     * @return State The final state of the Docker container, RUNNING if successfully started, otherwise its current state.
     */
    @SneakyThrows
    @Override
    public String startResource(ResourceContext resourceContext) {
        DockerContainer container = containerRepository.getContainerByID(resourceContext.getResourceId());
        logger.info("Starting docker");
        String[] command = {"sudo", "docker", "start", container.getID()};
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        }
        catch (IOException | InterruptedException e) {
            logger.error("Error starting docker waiting for completion", e);
            return "UNKNOWN";
        }
        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if (Objects.equals(container.getState(), "RUNNING")) {
                return "RUNNING";
            }
            Thread.sleep(1000);
        }
        logger.info("returning state of container: {}", container.getState());
        return  container.getState();
    }


    @SneakyThrows
    public void deleteContainer(ResourceContext resourceContext) {
        DockerContainer container = containerRepository.getContainerByID(resourceContext.getResourceId());
        logger.info("Deleting container");
        String[] command = {"sudo", "docker", "rm -f", container.getID()};
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        }
        catch (IOException | InterruptedException e) {
            logger.error("Error starting docker waiting for completion", e);
        }
        int attempts = 10;
        for (int i = 0; i < attempts; i++) {
            if(container.getID().isEmpty()) {
                logger.info("container was successfully deleted");
            }
            Thread.sleep(1000);
        }
        logger.info("returning state of container: {}", container.getState());
    }
}
