package monitoring.serverResources.disk;
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

    /**
     * Retrieves disk usage information from the system.
     *
     * Executes a command to get disk usage details and parses the output into a list of DiskUsage objects.
     * Throws an IOException if the command execution returns no output.
     *
     * @return List<DiskUsage> A list of DiskUsage objects representing the disk usage information.
     * @throws IOException If the command execution fails or returns no output.
     */
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

    /**
     * Parses a single line of disk usage command output into a DiskUsage object.
     *
     * Splits the line into parts based on whitespace and assigns these parts to the respective fields
     * of the DiskUsage object. Handles missing or incomplete data gracefully by assigning default values.
     *
     * @param line A string representing a single line of disk usage output.
     * @return DiskUsage A DiskUsage object populated with values parsed from the line.
     */
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
}