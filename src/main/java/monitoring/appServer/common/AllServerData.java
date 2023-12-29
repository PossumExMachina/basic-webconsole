package monitoring.appServer.common;

import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class AllServerData {

    private final List<String> runningApplications;
    private final List<String> notRunningApplications;
    private final boolean isTomcatRunning;
    private final List<String> diskUsage;
    private final List<String> freeMemory;

    public AllServerData(List<String> runningApplications, List<String> notRunningApplications, boolean isTomcatRunning, List<String> diskUsage, List<String> freeMemory) {
        this.runningApplications = runningApplications;
        this.notRunningApplications = notRunningApplications;
        this.isTomcatRunning = isTomcatRunning;
        this.diskUsage = diskUsage;
        this.freeMemory = freeMemory;
    }


}