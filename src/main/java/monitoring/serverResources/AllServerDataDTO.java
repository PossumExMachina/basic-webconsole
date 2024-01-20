package monitoring.serverResources;

import lombok.Getter;
import monitoring.appServer.application.Application;
import monitoring.docker.DockerContainer;
import monitoring.serverResources.disk.DiskUsage;
import monitoring.serverResources.memory.Memory;

import java.util.List;
@Getter
public class AllServerDataDTO {

    private final List<DockerContainer> dockerContainers;
    private final List<DiskUsage> diskUsage;
    private final List<Memory> freeMemory;

    public AllServerDataDTO(List<DiskUsage> diskUsage, List<Memory> freeMemory, List<DockerContainer> dockerContainers) {
        this.diskUsage = diskUsage;
        this.freeMemory = freeMemory;
        this.dockerContainers = dockerContainers;
    }


}