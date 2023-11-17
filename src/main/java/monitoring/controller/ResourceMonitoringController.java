package monitoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ResourceMonitoringController {

    private final ResourceService resourceService;

    public ResourceMonitoringController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/resources")
    public ResourceData getResources() throws IOException, InterruptedException {
        return resourceService.getServerResources();
    }
}



