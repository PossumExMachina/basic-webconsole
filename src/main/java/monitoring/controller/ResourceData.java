package monitoring.controller;

public class ResourceData {
    private double cpuUsage;
    private double memoryUsage;
    private long freeDiskSpace;


    public ResourceData(double cpuUsage, double memoryUsage, long freeDiskSpace) {
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.freeDiskSpace = freeDiskSpace;
    }


    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public long getFreeDiskSpace() {
        return freeDiskSpace;
    }

    public void setFreeDiskSpace(long freeDiskSpace) {
        this.freeDiskSpace = freeDiskSpace;
    }
}
