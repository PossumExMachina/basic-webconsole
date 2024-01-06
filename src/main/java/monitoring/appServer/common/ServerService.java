package monitoring.appServer.common;

import monitoring.appServer.application.Application;
import monitoring.appServer.application.ApplicationService;
import monitoring.appServer.tomcat.TomcatService;
import monitoring.docker.DockerContainer;
import monitoring.docker.DockerContainerService;
import monitoring.serverResources.disk.DiskService;
import monitoring.serverResources.disk.DiskUsage;
import monitoring.serverResources.memory.Memory;
import monitoring.serverResources.memory.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ServerService {


    @Autowired
    private TomcatService tomcatService;

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private DiskService diskService;

    @Autowired
    private DockerContainerService dockerContainerService;
    @Autowired
    ApplicationService applicationService;


    public AllServerDataDTO getApplicationStatusResource() throws IOException {
        State tomcatState = tomcatService.getTomcatState();
        List<Application> applications = applicationService.getApplications();
        List<DiskUsage> diskUsage = diskService.getDiskUsage();
        List<Memory> freeMemory = memoryService.getFreeMemory();
        List<DockerContainer> dockerContainers = dockerContainerService.getDockerContainers();
        return new AllServerDataDTO(applications, tomcatState, diskUsage, freeMemory, dockerContainers);
    }


}

