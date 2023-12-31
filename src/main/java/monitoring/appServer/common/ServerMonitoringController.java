package monitoring.appServer.common;

import monitoring.appServer.tomcat.TomcatService;
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
    private ServerService serverService;

    @Autowired
    private TomcatService tomcatService;



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

    @PostMapping("/tomcat/start")
    public ResponseEntity<?> startTomcat() {
        State state = tomcatService.startTomcat();
        if (state == State.RUNNING) {
            return ResponseEntity.ok("Tomcat started successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error starting Tomcat.");
        }
    }

    @PostMapping("/tomcat/stop")
    public ResponseEntity<?> stopTomcat() {
        State state = tomcatService.stopTomcat();
        if (state == State.STOPPED) {
            return ResponseEntity.ok("Tomcat stopped successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error stopping Tomcat.");
        }
    }


  


}
