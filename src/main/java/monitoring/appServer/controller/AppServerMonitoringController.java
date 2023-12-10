package monitoring.appServer.controller;

import monitoring.appServer.tomcat.TomcatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@RestController
public class AppServerMonitoringController {

@Autowired
    private AppServerService resourceService;

    @Autowired
    private TomcatService tomcatService;

    public AppServerMonitoringController() {

    }


    @GetMapping("/")
    public ModelAndView getResources() throws IOException {
        ModelAndView modelAndView = new ModelAndView("resourcesTemplate");
        AppServerData resourceData = resourceService.getApplicationStatusResource();
        modelAndView.addObject("resourceData", resourceData);
        return modelAndView;
    }

    @GetMapping("/tomcat")
    public ModelAndView getTomcat() throws IOException {
        ModelAndView modelAndView = new ModelAndView("tomcat");
        AppServerData resourceData = resourceService.getApplicationStatusResource();
        modelAndView.addObject("resourceData", resourceData);
        return modelAndView;
    }

    @PostMapping("/tomcat/stop")
    public ModelAndView stopTomcat() {
        tomcatService.stopTomcat();
        return new ModelAndView("redirect:confirmation");
    }

    @GetMapping("/tomcat/confirmation")
    public ModelAndView getConfirmation() {
        return new ModelAndView("confirmation");
    }

    @PostMapping("/tomcat/start")
    public ModelAndView startTomcat() {
        tomcatService.startTomcat();
        return new ModelAndView("redirect:/tomcat/confirmation");
    }

}



