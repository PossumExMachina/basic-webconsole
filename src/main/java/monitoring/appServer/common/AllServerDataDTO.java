package monitoring.appServer.common;

import lombok.Getter;
import monitoring.appServer.application.Application;
import monitoring.docker.DockerContainer;

import java.util.List;
@Getter
public class AllServerDataDTO {

    private final List<Application> applications;

    private final State tomcatState;
    private final List<DockerContainer> dockerContainers;
    private final List<String> diskUsage;
    private final List<String> freeMemory;

    public AllServerDataDTO(List<Application> applications, State tomcatState, List<String> diskUsage, List<String> freeMemory, List<DockerContainer> dockerContainers) {
        this.tomcatState = tomcatState;
        this.diskUsage = diskUsage;
        this.freeMemory = freeMemory;
        this.dockerContainers = dockerContainers;
        this.applications = applications;
    }


}