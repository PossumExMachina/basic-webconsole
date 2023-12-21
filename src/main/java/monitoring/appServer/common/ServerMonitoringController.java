package monitoring.appServer.common;

import monitoring.appServer.tomcat.TomcatService;
import monitoring.appServer.tomcat.TomcatState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@RestController
public class ServerMonitoringController {

    @Autowired
    private ServerService resourceService;

    @Autowired
    private TomcatService tomcatService;

    public ServerMonitoringController() {

    }


    @GetMapping("/")
    public ModelAndView getResources() throws IOException {
        ModelAndView modelAndView = new ModelAndView("resourcesTemplate");
        AllServerData resourceData = resourceService.getApplicationStatusResource();
        modelAndView.addObject("resourceData", resourceData);
        return modelAndView;
    }

    @GetMapping("/tomcat")
    public ModelAndView getTomcat() throws IOException {
        ModelAndView modelAndView = new ModelAndView("tomcat");
        AllServerData resourceData = resourceService.getApplicationStatusResource();
        modelAndView.addObject("resourceData", resourceData);
        return modelAndView;
    }


    @GetMapping("/tomcat/confirmation")
    public ModelAndView getConfirmation() {
        return new ModelAndView("confirmation");
    }

    @GetMapping("/tomcat/actionError")
    public ModelAndView getErrorPage() {
        return new ModelAndView("actionError");
    }

    @PostMapping("/tomcat/start")
    public ModelAndView startTomcat() {
        if (tomcatService.startTomcat() == TomcatState.RUNNING) {
            return new ModelAndView("redirect:/tomcat/confirmation");
        }
        else return new ModelAndView("redirect:/tomcat/actionError");
    }

    @PostMapping("/tomcat/stop")
    public ModelAndView stopTomcat() {
        if (tomcatService.stopTomcat() == TomcatState.STOPPED) {
            return new ModelAndView("redirect:/tomcat/confirmation");
        }
        else return new ModelAndView("redirect:/tomcat/actionError");
    }

}



