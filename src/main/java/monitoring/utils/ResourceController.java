package monitoring.utils;

import monitoring.commands.control.CommandStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResourceController {

    @Autowired
    private DetectResource detectResource;

    @Autowired
    private CommandStrategy commandStrategy;

    @GetMapping("/resources/availability")
    public ResourceAvailability getResourcesAvailability() {
        boolean dockerAvailable = detectResource.resourcePresent(commandStrategy.getDockerInstalledCmd(), commandStrategy.getDockeControlOutput());
       // boolean tomcatAvailable = detectResource.resourcePresent(commandStrategy., "expected-output-for-tomcat");

        return new ResourceAvailability(dockerAvailable);
    }

    public static class ResourceAvailability {
        public boolean dockerAvailable;
      //  public boolean tomcatAvailable;

        public ResourceAvailability(boolean dockerAvailable) {
            this.dockerAvailable = dockerAvailable;
         //   this.tomcatAvailable = tomcatAvailable;
        }

    }
}
