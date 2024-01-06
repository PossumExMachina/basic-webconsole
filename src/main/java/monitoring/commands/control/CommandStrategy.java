package monitoring.commands.control;

import org.springframework.stereotype.Component;

@Component
public interface CommandStrategy {
    String getDockerInstalledCmd();
    String getFreeMemoryCmd();
    String getListContainersCmd();

    String getStartTomcatCmd();

    String getStopTomcatCmd();

    String getDiskUsageCmd();

    String getDockeControlOutput();

}
