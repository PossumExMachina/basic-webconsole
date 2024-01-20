package monitoring.serverResources;

import monitoring.docker.DockerContainer;
import monitoring.docker.DockerContainerService;
import monitoring.serverResources.AllServerDataDTO;
import monitoring.serverResources.disk.DiskService;
import monitoring.serverResources.disk.DiskUsage;
import monitoring.serverResources.memory.Memory;
import monitoring.serverResources.memory.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ServerDataService {



    @Autowired
    private MemoryService memoryService;

    @Autowired
    private DiskService diskService;

    @Autowired
    private DockerContainerService dockerContainerService;



    public AllServerDataDTO getApplicationStatusResource() throws IOException {
        List<DiskUsage> diskUsage = diskService.getDiskUsage();
        List<Memory> freeMemory = memoryService.getFreeMemory();
        List<DockerContainer> dockerContainers = dockerContainerService.getDockerContainers();
        return new AllServerDataDTO(diskUsage, freeMemory, dockerContainers);
    }


}

