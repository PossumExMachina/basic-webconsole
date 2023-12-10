package monitoring.appServer.controller;

import monitoring.appServer.application.ApplicationService;
import monitoring.appServer.tomcat.TomcatService;
import monitoring.serverResources.disk.DiskService;
import monitoring.serverResources.memory.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AppServerService {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TomcatService tomcatService;

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private DiskService diskService;

    public AppServerService() {

    }

    public AppServerData getApplicationStatusResource() throws IOException {
        boolean isTomcatRunning = tomcatService.isTomcatRunning();
        List<String> runningApps = applicationService.getRunningApplications();
        List<String> notRunningApps = applicationService.getNotRunningApplications();
        List<String> diskUsage = diskService.getDiskUsage();
        List<String> freeMemory = memoryService.getFreeMemory();

        return new AppServerData(runningApps, notRunningApps, isTomcatRunning, diskUsage, freeMemory);
    }


}

