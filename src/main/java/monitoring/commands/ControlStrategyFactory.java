package monitoring.commands;

import monitoring.appServer.tomcat.TomcatControlService;
import monitoring.commands.control.ControlStrategy;
import monitoring.docker.DockerContainerService;
import monitoring.docker.DockerControlService;
import monitoring.docker.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.directory.InvalidAttributeValueException;
import java.io.IOException;
import java.util.Objects;


@Component
public class ControlStrategyFactory {

    @Autowired
    private TomcatControlService tomcatControlService;

    @Autowired
    private DockerControlService dockerControlService;

    @Autowired
    private DockerContainerService dockerContainerService;


    /**
     * Retrieves a ControlStrategy based on the specified resource type.
     *
     * This method selects and returns a specific ControlStrategy depending on the resource type provided.
     * It supports 'tomcat' for TomcatControlService and checks for matching Docker container IDs for DockerControlService.
     * If no matching strategy is found, an InvalidAttributeValueException is thrown.
     *
     * @param resourceType The type of the resource for which the control strategy is required.
     * @return ControlStrategy The strategy corresponding to the given resource type.
     * @throws IOException If an I/O error occurs.
     * @throws InvalidAttributeValueException If the resource type does not match any known strategy.
     */
    public ControlStrategy getStrategy(String resourceType) throws IOException, InvalidAttributeValueException {
        if (resourceType.equals("tomcat")) {
            return tomcatControlService;
        }

        for (String id : dockerContainerService.getContainerID()) {
            if (Objects.equals(resourceType, id)) {
                return dockerControlService;
            }
        }

        throw new InvalidAttributeValueException("Invalid resource type: " + resourceType);
    }

}