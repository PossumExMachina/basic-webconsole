package monitoring.appServer.common;

import monitoring.appServer.tomcat.TomcatService;
import monitoring.appServer.tomcat.TomcatState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ServerMonitoringController {

    @Autowired
    private ServerService resourceService;

    @Autowired
    private TomcatService tomcatService;

    public ServerMonitoringController() {
    }

    @GetMapping("/resources")
    public ResponseEntity<AllServerData> getResources() throws IOException {
        AllServerData resourceData = resourceService.getApplicationStatusResource();
        return ResponseEntity.ok(resourceData);
    }

    @GetMapping("/tomcat")
    public ResponseEntity<AllServerData> getTomcat() throws IOException {
        AllServerData resourceData = resourceService.getApplicationStatusResource();
        return ResponseEntity.ok(resourceData);
    }

    @PostMapping("/tomcat/start")
    public ResponseEntity<?> startTomcat() {
        TomcatState state = tomcatService.startTomcat();
        if (state == TomcatState.RUNNING) {
            return ResponseEntity.ok("Tomcat started successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error starting Tomcat.");
        }
    }

    @PostMapping("/tomcat/stop")
    public ResponseEntity<?> stopTomcat() {
        TomcatState state = tomcatService.stopTomcat();
        if (state == TomcatState.STOPPED) {
            return ResponseEntity.ok("Tomcat stopped successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error stopping Tomcat.");
        }
    }


}
