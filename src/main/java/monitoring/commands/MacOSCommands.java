package monitoring.commands;

import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.appServer.tomcat.TomcatState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class MacOSCommands implements CommandStrategy{

    @Autowired
    private CommandExec commandExec;

    @Autowired
    private TomcatCommandService tomcatCommandService;

    @Override
    public List<String> getFreeMemory() {
        String command = """
                vm_stat | awk -v FS="[^0-9]+" '
                /Pages free/ {free=$2}
                /Pages active/ {active=$2}
                /Pages inactive/ {inactive=$2}
                /Pages speculative/ {speculative=$2}
                /Pages wired down/ {wired=$2}
                END {
                  used=(active+inactive+wired)*4096/1048576;
                  total=(free+active+inactive+speculative+wired)*4096/1048576;
                  free=free*4096/1048576;
                  printf "Total: %d MB\\nUsed: %d MB\\nFree: %d MB\\n", total, used, free
                }'
                """;
       List<String> memoryUsage; //TODO: remove duplicate code
        try {
            memoryUsage = commandExec.executeCommand(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (memoryUsage.isEmpty()) {
            try {
                throw new IOException("Failed to get memory usage: Command execution returned null");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return memoryUsage;
    }

    @Override
    public TomcatState stopServer(){
        String[] command = {"bash", "-c", "sudo /opt/tomcat/apache-tomcat-10.1.17/bin/shutdown.sh"};
        return tomcatCommandService.changeTomcatState(command);

    }

    @Override
    public TomcatState startServer() {
        String[] command = {"bash", "-c", "sudo /opt/tomcat/apache-tomcat-10.1.17/bin/startup.sh"};
        return tomcatCommandService.changeTomcatState(command);
    }


}
