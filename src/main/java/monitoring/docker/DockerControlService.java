package monitoring.docker;

import lombok.SneakyThrows;
import monitoring.common.ResourceContext;
import monitoring.commands.control.ControlStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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
        Optional<DockerContainer> optionalContainer = containerRepository.getContainerByID(resourceContext.getResourceId());
        if (optionalContainer.isPresent()) {
            DockerContainer foundContainer = optionalContainer.get();

            logger.info("Stopping docker");
            String[] command = {"sudo", "docker", "stop", foundContainer.getID()};
            try {
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                logger.error("Error stopping docker waiting for completion", e);
                return "UNKNOWN";
            }
            int attempts = 10;
            for (int i = 0; i < attempts; i++) {
                if (Objects.equals(foundContainer.getState(), "STOPPED")) {
                    return "STOPPED";
                }
                Thread.sleep(1000);
            }
            logger.info("returning state of container: {}", foundContainer.getState());
            return foundContainer.getState();
        }
        else throw new NotFoundException("Container with that id was not found");
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
        Optional<DockerContainer> optionalContainer = containerRepository.getContainerByID(resourceContext.getResourceId());
        if (optionalContainer.isPresent()) {
            DockerContainer foundContainer = optionalContainer.get();

            logger.info("Starting docker");
            String[] command = {"sudo", "docker", "start", foundContainer.getID()};
            try {
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                logger.error("Error starting docker waiting for completion", e);
                return "UNKNOWN";
            }
            int attempts = 10;
            for (int i = 0; i < attempts; i++) {
                if (Objects.equals(foundContainer.getState(), "RUNNING")) {
                    return "RUNNING";
                }
                Thread.sleep(1000);
            }
            logger.info("returning state of container: {}", foundContainer.getState());
            return foundContainer.getState();
        }
        else throw new NotFoundException("Container with that id was not found");
    }


    @SneakyThrows
    public void deleteContainer(String id) {
        Optional<DockerContainer> optionalContainer = containerRepository.getContainerByID(id);
        if (optionalContainer.isPresent()) {
            DockerContainer foundContainer = optionalContainer.get();
            logger.info("Deleting container");
            String[] command = {"sudo", "docker", "rm -f", foundContainer.getID()};
            try {
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                logger.error("Error deleting docker container", e);
            }
            int attempts = 10;
            for (int i = 0; i < attempts; i++) {
                if (foundContainer.getID().isEmpty()) {
                    logger.info("container was successfully deleted");
                }
                Thread.sleep(1000);
            }
            logger.info("Could not delete selected container");
        }
    }
}
