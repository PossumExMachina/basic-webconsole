package monitoring.commands;

import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.appServer.tomcat.TomcatState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LinuxCommands implements CommandStrategy {

    @Autowired
    CommandExec commandExec;

    @Autowired
    private TomcatCommandService tomcatCommandService;

    @Override
    public List<String> getFreeMemory() {
        String command = "free -m";
        List<String> memoryUsage;
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
        String[] command = {"bash", "-c", "systemctl stop tomcat"};
        return tomcatCommandService.changeTomcatState(command);

    }

    @Override
    public TomcatState startServer() {
        String[] command = {"bash", "-c", "systemctl start tomcat"};
        return tomcatCommandService.changeTomcatState(command);
    }




}
