package monitoring.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LinuxCommands implements CommandStrategy {

    @Autowired
    CommandExec commandExec;


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






}
