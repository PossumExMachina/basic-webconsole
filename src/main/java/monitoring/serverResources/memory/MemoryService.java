package monitoring.serverResources.memory;

import monitoring.CommandExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemoryService {

    @Autowired
    CommandExec commandExec;

    public MemoryService() {
    }

    public List<String> getFreeMemory() throws IOException {
        String command = "ls /Users/";
        List<String> memoryUsage = commandExec.executeCommand(command);
        if (memoryUsage == null) {
            throw new IOException("Failed to get disk usage: Command execution returned null");
        }
        return memoryUsage;
    }
}



