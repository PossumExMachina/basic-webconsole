package monitoring.appServer.common;

import monitoring.appServer.application.ApplicationStatusService;
import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.serverResources.disk.DiskService;
import monitoring.serverResources.memory.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ServerService {

    @Autowired
    private ApplicationStatusService applicationStatusService;

    @Autowired
    private TomcatCommandService tomcatCommandService;

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private DiskService diskService;

    public ServerService() {
    }

    public AllServerData getApplicationStatusResource() throws IOException {
        boolean isTomcatRunning = tomcatCommandService.isTomcatRunning();
        List<String> runningApps = applicationStatusService.getRunningApps();
        List<String> notRunningApps = applicationStatusService.getNotRunningApps();
        List<String> diskUsage = diskService.getDiskUsage();
        List<String> freeMemory = memoryService.getFreeMemory();
        return new AllServerData(runningApps, notRunningApps, isTomcatRunning, diskUsage, freeMemory);
    }


}

