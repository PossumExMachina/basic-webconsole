package monitoring.controller;

import monitoring.tomcat.TomcatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/resources")
public class ResourceMonitoringController {


    private final ResourceService resourceService;
    private final TomcatService tomcatService;

    public ResourceMonitoringController(ResourceService resourceService, TomcatService tomcatService) {
        this.resourceService = resourceService;
        this.tomcatService = tomcatService;
    }


    @GetMapping("")
    public ModelAndView getResources() {
        ModelAndView modelAndView = new ModelAndView("resourcesTemplate");
        ResourceData resourceData = resourceService.getApplicationStatusResource();
        modelAndView.addObject("resourceData", resourceData);
        return modelAndView;
    }



    @GetMapping("/tomcat")
    public ModelAndView getTomcat() {
        ModelAndView modelAndView = new ModelAndView("tomcat");
        ResourceData resourceData = resourceService.getApplicationStatusResource();
        modelAndView.addObject("resourceData", resourceData);
        return modelAndView;
    }

    @PostMapping("/tomcat/stop")
    public ModelAndView stopTomcat() {
        tomcatService.stopTomcat();
        return new ModelAndView("redirect:/resources");
    }

    @PostMapping("/tomcat/start")
    public ModelAndView startTomcat() {
        tomcatService.startTomcat();
        return new ModelAndView("redirect:/resources");
    }

}



