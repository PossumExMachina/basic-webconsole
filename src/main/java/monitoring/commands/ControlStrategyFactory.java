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