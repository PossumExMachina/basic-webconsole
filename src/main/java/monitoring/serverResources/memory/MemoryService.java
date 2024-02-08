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
import java.util.stream.Collectors;

@Service
public class MemoryService {

    @Autowired
    private CommandStrategy commandStrategy;

    @Autowired
    private CommandExec commandExec;

    private static final Logger logger = LoggerFactory.getLogger(DockerControlService.class);

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

    private Memory parseLine(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length >= 3) {
            String total = parts[0];
            String used = parts[2];
            String free = parts[4];

            logger.info("Total: " + total + ", Used: " + used + ", Free: " + free);
            return new Memory(total, used, free);
        } else {
            logger.error("Unexpected format for line: {}", line);
            return null;
        }
    }
}


