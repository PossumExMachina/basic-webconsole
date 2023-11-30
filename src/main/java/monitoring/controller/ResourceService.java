package monitoring.controller;

import monitoring.application.ApplicationService;
import monitoring.application.ApplicationStatus;
import monitoring.tomcat.TomcatService;
import monitoring.tomcat.TomcatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ResourceService {
    private ApplicationService applicationService;
    private TomcatService tomcatService;


    @Autowired
    public ResourceService(ApplicationService applicationService, TomcatService tomcatService) {
        this.applicationService = applicationService;
        this.tomcatService = tomcatService;
    }

    public ResourceData getApplicationStatusResource() {
        boolean isTomcatRunning = tomcatService.isTomcatRunning();
        List<String> runningApps = applicationService.getRunningApplications();
        List<String> notRunningApps = applicationService.getNotRunningApplications();

        return new ResourceData(runningApps, notRunningApps, isTomcatRunning);
    }


}

