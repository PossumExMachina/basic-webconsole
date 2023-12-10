package monitoring.appServer.controller;

import java.util.List;

public class AppServerData {
    private List<String> runningApplications;
    private List<String> notRunningApplications;
    private boolean isTomcatRunning;
    private List<String> diskUsage;
    private List<String> freeMemory;



    public AppServerData(List<String> runningApplications, List<String> notRunningApplications, boolean isTomcatRunning, List<String> diskUsage, List<String> freeMemory) {
        this.runningApplications = runningApplications;
        this.notRunningApplications = notRunningApplications;
        this.isTomcatRunning = isTomcatRunning;
        this.diskUsage = diskUsage;
        this.freeMemory = freeMemory;
    }

    public List<String> getRunningApplications() {
        return runningApplications;
    }


    public List<String> getNotRunningApplications() {
        return notRunningApplications;
    }

    public boolean isTomcatRunning() {
        return isTomcatRunning;
    }

    public List<String> getDiskUsage() {
        return diskUsage;
    }

    public List<String> getFreeMemory() {
        return freeMemory;
    }
}