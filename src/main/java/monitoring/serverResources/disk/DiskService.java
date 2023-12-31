package monitoring.serverResources.disk;
import monitoring.commands.CommandExec;
import monitoring.commands.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class DiskService {

    @Autowired
    CommandExec commandExec;

    public DiskService() {
    }

    public List<String> getDiskUsage() throws IOException {
        List<String> diskUsage = commandExec.executeCommand(Commands.getDiskUsage);
        if (diskUsage != null && !diskUsage.isEmpty()) {
            return diskUsage;
        } else {
            throw new IOException("Failed to get memory usage: No output from command");
        }
    }

}