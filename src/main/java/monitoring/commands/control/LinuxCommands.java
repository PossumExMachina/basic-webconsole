package monitoring.commands.control;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public final class LinuxCommands implements CommandStrategy {

    public static final String dockerExists = "docker -v";
    public static final String dockerControlOutput = "Docker version";
    public static final String listContainers = "docker ps -a";

    public static final String tomcatExists = "ls" + System.getenv("CATALINA_HOME");
    public static final String tomcatControlOutput = "webapps";
    public static final String startTomcat = "sudo systemctl start tomcat";
    public static final String stopTomcat = "sudo systemctl stop tomcat";
    public static final String getDiskUsage = "df -h";
    public static final String getFreeMemory = "free -m";




    public LinuxCommands(){

    }

    @Override
    public String getDockerInstalledCmd() {
        return dockerExists;
    }

    @Override
    public String getFreeMemoryCmd() {
        return getFreeMemory;
    }

    @Override
    public String getListContainersCmd() {
        return listContainers;
    }

    @Override
    public String getStartTomcatCmd() {
        return startTomcat;
    }

    @Override
    public String getStopTomcatCmd() {
        return startTomcat;
    }

    @Override
    public String getTomcatInstalledCmd() {
        return tomcatExists;
    }

    @Override
    public String getTomcatControlOutput() {
        return tomcatControlOutput;
    }

    @Override
    public String getDiskUsageCmd() {
        return getDiskUsage;
    }

    @Override
    public String getDockeControlOutput() {
        return dockerControlOutput;
    }


}
