package monitoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ResourceMonitoringController {

    private final ResourceService resourceService;

    public ResourceMonitoringController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }


    @GetMapping("/resources")
    public ModelAndView getResources() {
        ModelAndView modelAndView = new ModelAndView("resourcesTemplate");
        ResourceData resourceData = resourceService.getApplicationStatusResource();
        modelAndView.addObject("resourceData", resourceData);
        return modelAndView;
    }




}



