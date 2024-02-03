package monitoring.docker;

import com.fasterxml.jackson.databind.ObjectMapper;
import monitoring.commands.control.ControlStrategy;
import monitoring.common.ResourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.InvalidAttributeValueException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class DockerController {

    @Autowired
    private DockerContainerService dockerContainerService;
    @Autowired
    private ResourceContext resourceContext;
    @Autowired
    private DockerControlService dockerControlService;

    @GetMapping("/dockerInfo")
    public ResponseEntity<String> getResources() throws IOException {
        List<DockerContainer> containers = dockerContainerService.parseDockerJson();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse;

        try {
            jsonResponse = mapper.writeValueAsString(containers);
        } catch (Exception e) {
            jsonResponse = "{\"error\": \"Error converting to JSON.\"}";
        }

        return ResponseEntity.ok(jsonResponse);
    }


    @PostMapping("/docker/{id}")
    public ResponseEntity<?> deleteContainer(@PathVariable String containerID) throws IOException, InvalidAttributeValueException {
      //  ControlStrategy strategy = controlStrategyFactory.getStrategy(resourceType);
      //  resourceContext.setResourceId(resourceType);
        //String state = strategy.stopResource(resourceContext);
        dockerControlService.deleteContainer(containerID);

        if (Objects.equals(state, "STOPPED")) {
            return ResponseEntity.ok(resourceType + " stopped successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error stopping " + resourceType + ".");
        }
    }

}
