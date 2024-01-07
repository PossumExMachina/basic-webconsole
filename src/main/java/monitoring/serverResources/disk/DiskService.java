package monitoring.serverResources.disk;
import monitoring.appServer.application.ApplicationService;
import monitoring.commands.CommandExec;
import monitoring.commands.control.CommandStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiskService {

    private static final Logger logger = LoggerFactory.getLogger(DiskService.class);

    @Autowired
    private CommandExec commandExec;


    @Autowired
    private CommandStrategy commandStrategy;

    public List<DiskUsage> getDiskUsage() throws IOException {
        List<String> outputLines = commandExec.executeCommand(commandStrategy.getDiskUsageCmd());
        if (outputLines == null || outputLines.isEmpty()) {
            throw new IOException("Failed to get disk usage: No output from command");
        }

        return outputLines.stream()
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }


    private DiskUsage parseLine(String line) {
        String[] parts = line.split("\\s+");

        String fileSystem = parts.length > 0 ? parts[0] : "N/A";
        String fileSystemSize = parts.length > 1 ? parts[1] : "N/A";
        String used = parts.length > 2 ? parts[2] : "N/A";
        String available = parts.length > 3 ? parts[3] : "N/A";
        String capacityString = parts.length > 4 ? parts[4].replaceAll("\\D", "") : "0";
        int capacity = capacityString.isEmpty() ? 0 : Integer.parseInt(capacityString);
        String iused = "iused";
        String ifree = "ifree";
        String mountedOn = parts.length > 5 ? parts[5] : "N/A";

        return new DiskUsage(fileSystem, fileSystemSize, used, available, capacity, iused, ifree, mountedOn);
    }




//    public List<String> getDiskUsage() throws IOException {
//        List<String> diskUsage = commandExec.executeCommand(LinuxCommands.getDiskUsage);
//        if (diskUsage != null && !diskUsage.isEmpty()) {
//            return diskUsage;
//        } else {
//            throw new IOException("Failed to get memory usage: No output from command");
//        }
//    }

}