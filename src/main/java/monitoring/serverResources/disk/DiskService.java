package monitoring.serverResources.disk;
import monitoring.appServer.common.State;
import monitoring.commands.CommandExec;
import monitoring.commands.Commands;
import monitoring.docker.DockerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiskService {

    @Autowired
    CommandExec commandExec;

    public DiskService() {
    }


    public List<DiskUsage> getDiskUsage() throws IOException {
        List<String> outputLines = commandExec.executeCommand(Commands.getDiskUsage);
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
        StringBuilder createdBuilder = new StringBuilder();

        String fileSystem = parts[0];
        String fileSystemSize = parts[1];
        String used = parts[2];
        String available = parts[3];
        String capacityString = parts[4].replaceAll("\\D", "");
        int capacity = Integer.parseInt(capacityString);
        String iused = parts[5];
        String ifree = parts[6];
        String mountedOn = parts[8];

        return new DiskUsage(fileSystem, fileSystemSize, used, available, capacity, iused, ifree, mountedOn);
    }



//    public List<String> getDiskUsage() throws IOException {
//        List<String> diskUsage = commandExec.executeCommand(Commands.getDiskUsage);
//        if (diskUsage != null && !diskUsage.isEmpty()) {
//            return diskUsage;
//        } else {
//            throw new IOException("Failed to get memory usage: No output from command");
//        }
//    }

}