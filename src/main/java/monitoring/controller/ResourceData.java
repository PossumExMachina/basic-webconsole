package monitoring.controller;

public class ResourceData {
    private boolean isTomcatRunning;


    public ResourceData(boolean isTomcatRunning) {
        this.isTomcatRunning = isTomcatRunning;
    }

    public boolean isTomcatRunning() {
        return isTomcatRunning;
    }

    public void setTomcatRunning(boolean tomcatRunning) {
        isTomcatRunning = tomcatRunning;
    }
}
