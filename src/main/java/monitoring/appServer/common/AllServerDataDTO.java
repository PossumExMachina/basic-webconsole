package monitoring.appServer.common;

import lombok.Getter;
import monitoring.appServer.application.Application;
import monitoring.docker.DockerContainer;
import org.springframework.stereotype.Component;

import java.util.List;
@Getter
public class AllServerDataDTO {

    private final List<Application> applications;

    private final boolean isTomcatRunning;
    private final List<DockerContainer> dockerContainers;
    private final List<String> diskUsage;
    private final List<String> freeMemory;

    public AllServerDataDTO(List<Application> applications, boolean isTomcatRunning, List<String> diskUsage, List<String> freeMemory, List<DockerContainer> dockerContainers) {
        this.isTomcatRunning = isTomcatRunning;
        this.diskUsage = diskUsage;
        this.freeMemory = freeMemory;
        this.dockerContainers = dockerContainers;
        this.applications = applications;
    }


}