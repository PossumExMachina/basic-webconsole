package monitoring.serverResources.memory;

import monitoring.commands.CommandExec;
import monitoring.commands.control.CommandStrategy;
import monitoring.docker.DockerControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MemoryService {

    @Autowired
    private CommandStrategy commandStrategy;

    @Autowired
    private CommandExec commandExec;

    private static final Logger logger = LoggerFactory.getLogger(DockerControlService.class);


    /**
     * Retrieves free memory information from the system.
     *
     * Executes a command to get free memory details and parses the output into a list of Memory objects.
     * Throws an IOException if the command execution returns no output.
     *
     * @return List<Memory> A list of Memory objects representing the free memory information.
     * @throws IOException If the command execution fails or returns no output.
     */
    public List<Memory> getFreeMemory() throws IOException {
        List<String> outputLines = commandExec.executeCommand(commandStrategy.getFreeMemoryCmd());
        if (outputLines == null || outputLines.isEmpty()) {
            throw new IOException("Failed to get memory usage: No output from command");
        }

        return outputLines.stream()
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }


    /**
     * Parses a single line of memory usage command output into a Memory object.
     *
     * Uses regular expressions to match the expected format of the memory information.
     * Logs an error and returns null if the line does not match the expected pattern.
     *
     * @param line A string representing a single line of memory usage output.
     * @return Memory A Memory object populated with values parsed from the line, or null if parsing fails.
     */
    private Memory parseLine(String line) {
        Pattern pattern = Pattern.compile("([\\w]+)\\s+([0-9]+)\\s+([0-9]+)\\s+([0-9]+).*");
        Matcher matcher = pattern.matcher(line);

        if (!matcher.matches()) {
            logger.error("Could not match the pattern for line: {}", line);
            return null; // or throw a custom exception
        }

        String total = matcher.group(2);
        String used = matcher.group(3);
        String free = matcher.group(4);

        return new Memory(total, used, free);
    }
}



