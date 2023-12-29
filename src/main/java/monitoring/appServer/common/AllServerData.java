package monitoring.appServer.common;

import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class AllServerData {

    @Getter
    private final List<String> runningApplications;
    @Getter
    private final List<String> notRunningApplications;
    private final boolean isTomcatRunning;
    @Getter
    private final List<String> diskUsage;
    @Getter
    private final List<String> freeMemory;

    public AllServerData(List<String> runningApplications, List<String> notRunningApplications, boolean isTomcatRunning, List<String> diskUsage, List<String> freeMemory) {
        this.runningApplications = runningApplications;
        this.notRunningApplications = notRunningApplications;
        this.isTomcatRunning = isTomcatRunning;
        this.diskUsage = diskUsage;
        this.freeMemory = freeMemory;
    }

    public boolean isTomcatRunning() {
        return isTomcatRunning;
    }

}