package monitoring.commands;

import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.appServer.tomcat.TomcatService;
import monitoring.appServer.tomcat.TomcatState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
@Component
public class LinuxCommands implements CommandStrategy {

    @Autowired
    CommandExec commandExec;

    @Autowired
    private TomcatCommandService tomcatCommandService;

    @Override
    public List<String> getFreeMemory() {
        String command = "free -m";
        List<String> memoryUsage = null;
        try {
            memoryUsage = commandExec.executeCommand(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (memoryUsage == null) {
            try {
                throw new IOException("Failed to get disk usage: Command execution returned null");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return memoryUsage;
    }

    @Override
    public TomcatState stopServer(){
        String[] command = {"bash", "-c", "systemctl stop tomcat"};
        return tomcatCommandService.changeTomcatState(command);

    }

    @Override
    public TomcatState startServer() {
        String[] command = {"bash", "-c", "systemctl start tomcat"};
        return tomcatCommandService.changeTomcatState(command);
    }


}
