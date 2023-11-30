package monitoring.controller;

import java.util.List;

public class ResourceData {
    private List<String> runningApplications;
    private List<String> notRunningApplications;
    private boolean isTomcatRunning;


    public ResourceData(List<String> runningApplications, List<String> notRunningApplications, boolean isTomcatRunning) {
        this.runningApplications = runningApplications;
        this.notRunningApplications = notRunningApplications;
        this.isTomcatRunning = isTomcatRunning;
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


}