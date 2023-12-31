package monitoring.appServer.common;

import monitoring.appServer.application.Application;
import monitoring.appServer.application.ApplicationService;
import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.docker.DockerContainer;
import monitoring.docker.DockerContainerService;
import monitoring.serverResources.disk.DiskService;
import monitoring.serverResources.memory.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ServerService {


    @Autowired
    private TomcatCommandService tomcatCommandService;

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private DiskService diskService;

    @Autowired
    private DockerContainerService dockerContainerService;
    @Autowired
    ApplicationService applicationService;


    public AllServerDataDTO getApplicationStatusResource() throws IOException {
        boolean isTomcatRunning = tomcatCommandService.isTomcatRunning();
        List<Application> applications = applicationService.getApplications();
        List<String> diskUsage = diskService.getDiskUsage();
        List<String> freeMemory = memoryService.getFreeMemory();
        List<DockerContainer> dockerContainers = dockerContainerService.getDockerContainers();
        return new AllServerDataDTO(applications, isTomcatRunning, diskUsage, freeMemory, dockerContainers);
    }


}

