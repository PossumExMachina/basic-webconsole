package monitoring.commands.control;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public final class LinuxCommands implements CommandStrategy {

    public static final String dockerExists = "docker -v";
    public static final String dockerControlOutput = "docker version";
    public static final String listContainers = """
            docker container ls -a --format '{{json .}}' | while read -r line; do
                containerID=$(echo "$line" | jq -r '.ID')
                image=$(echo "$line" | jq -r '.Image')
                created=$(echo "$line" | jq -r '.CreatedAt')
                container_status=$(echo "$line" | jq -r '.Status')
                names=$(echo "$line" | jq -r '.Names ')
                        
                echo "Container ID: $containerID"
                echo "Image: $image"
                echo "Created: $created"
                echo "Status: $container_status"
                echo "Names: $names"
                echo "-------------------------------------"
            done""";
    public static final String tomcatExists = "ls " + System.getenv("CATALINA_HOME");
    public static final String tomcatControlOutput = "webapps";
    public static final String startTomcat = "sudo systemctl start tomcat";
    public static final String stopTomcat = "sudo systemctl stop tomcat";
    public static final String getDiskUsage = "df -h";
    public static final String getFreeMemory = """
            free -m | awk '
                NR==1 {next}\s
                /Mem/ {
                    total=$2;\s
                    used=$3;\s
                    free=$4;\s
                    print "Total\\tUsed\\tFree";
                    print total " MB\\t" used " MB\\t" free " MB"
                }'
            """;

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
        return stopTomcat;
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
