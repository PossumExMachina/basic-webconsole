package monitoring.commands;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public final class Commands {

    public static final String dockerExists = "docker -v";
    public static final String dockerControlOutput = "Docker version";
    public static final String listContainers = "docker ps -a";

    public static final String startTomcat = "sudo systemctl start tomcat";
    public static final String stopTomcat = "sudo systemctl stop tomcat";
    public static final String getDiskUsage = "df -h";




    private Commands(){

    }

}
