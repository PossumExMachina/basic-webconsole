package monitoring.serverResources.disk;
import monitoring.commands.CommandExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DiskService {

    @Autowired
    CommandExec commandExec;

    public DiskService() {
    }

    public List<String> getDiskUsage() throws IOException {
        String command = "df -h";
        List<String> diskUsage = commandExec.executeCommand(command);
        if (diskUsage != null && !diskUsage.isEmpty()) {
            return diskUsage;
        } else {
            throw new IOException("Failed to get memory usage: No output from command");
        }
    }

}