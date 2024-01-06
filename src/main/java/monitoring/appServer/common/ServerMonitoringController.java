package monitoring.appServer.common;

import monitoring.appServer.tomcat.TomcatControlService;
import monitoring.commands.ControlStrategyFactory;
import monitoring.commands.control.ControlStrategy;
import monitoring.docker.DockerControlService;
import monitoring.docker.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.InvalidAttributeValueException;
import java.io.IOException;

@RestController
public class ServerMonitoringController {

    @Autowired
    private ServerService serverService;

    @Autowired
    private TomcatControlService tomcatControlService;

    @Autowired
    private DockerControlService dockerControlService;
    @Autowired
    private ControlStrategyFactory controlStrategyFactory;



    public ServerMonitoringController() {
    }

    @GetMapping("/resources")
    public ResponseEntity<AllServerDataDTO> getResources() throws IOException {
        AllServerDataDTO resourceData = serverService.getApplicationStatusResource();
        return ResponseEntity.ok(resourceData);
    }

    @GetMapping("/tomcat")
    public ResponseEntity<AllServerDataDTO> getTomcat() throws IOException {
        AllServerDataDTO resourceData = serverService.getApplicationStatusResource();
        return ResponseEntity.ok(resourceData);
    }

    @PostMapping("/{resourceType}/start")
    public ResponseEntity<?> startResource(@PathVariable String resourceType) throws IOException, InvalidAttributeValueException {
        ControlStrategy strategy = controlStrategyFactory.getStrategy(resourceType);
        State state = strategy.startResource();

        if (state == State.RUNNING) {
            return ResponseEntity.ok(resourceType + " started successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error starting " + resourceType + ".");
        }
    }

    @PostMapping("/{resourceType}/stop")
    public ResponseEntity<?> stopResource(@PathVariable String resourceType) throws IOException, InvalidAttributeValueException {
        ControlStrategy strategy = controlStrategyFactory.getStrategy(resourceType);
        State state = strategy.stopResource();

        if (state == State.STOPPED) {
            return ResponseEntity.ok(resourceType + " stopped successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error stopping " + resourceType + ".");
        }
    }





}




