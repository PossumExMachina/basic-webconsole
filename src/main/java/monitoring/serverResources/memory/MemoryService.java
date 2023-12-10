package monitoring.serverResources.memory;

import monitoring.CommandExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MemoryService {

    @Autowired
    CommandExec commandExec;

    public MemoryService() {
    }

    public List<String> getFreeMemory() throws IOException {
        String command = "free -m";
        List<String> diskUsage = commandExec.executeCommand(command);
        if (diskUsage != null && !diskUsage.isEmpty()) {
            return diskUsage;
        } else {
            throw new IOException("Failed to get memory usage: No output from command");
        }
    }
}



