package monitoring.serverResources;

import monitoring.common.ResourceContext;
import monitoring.common.State;
import monitoring.commands.ControlStrategyFactory;
import monitoring.commands.control.ControlStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.InvalidAttributeValueException;
import java.io.IOException;
import java.util.Objects;

@RestController
public class ServerDataController {

    @Autowired
    private ServerDataService serverService;

    @Autowired
    private ControlStrategyFactory controlStrategyFactory;

    @Autowired
    private ResourceContext resourceContext;

    @GetMapping("/tomcat")
    public ResponseEntity<AllServerDataDTO> getTomcat() throws IOException {
        AllServerDataDTO resourceData = serverService.getApplicationStatusResource();
        return ResponseEntity.ok(resourceData);
    }

    @PostMapping("/{resourceType}/start")
    public ResponseEntity<?> startResource(@PathVariable String resourceType) throws IOException, InvalidAttributeValueException {
        ControlStrategy strategy = controlStrategyFactory.getStrategy(resourceType);
        resourceContext.setResourceId(resourceType);
        String state = strategy.startResource(resourceContext);

        if (Objects.equals(state, "RUNNING")) {
            return ResponseEntity.ok(resourceType + " started successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error starting " + resourceType + ".");
        }
    }

    @PostMapping("/{resourceType}/stop")
    public ResponseEntity<?> stopResource(@PathVariable String resourceType) throws IOException, InvalidAttributeValueException {
        ControlStrategy strategy = controlStrategyFactory.getStrategy(resourceType);
        resourceContext.setResourceId(resourceType);
        String state = strategy.stopResource(resourceContext);

        if (Objects.equals(state, "STOPPED")) {
            return ResponseEntity.ok(resourceType + " stopped successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error stopping " + resourceType + ".");
        }
    }


}




