package monitoring.serverResources.memory;

import monitoring.commands.CommandExec;
import monitoring.commands.control.CommandStrategy;
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
        String[] parts = line.split("\\s+");

        String total = parts[0];
        String used = parts[2];
        String free = parts[4];

        return new Memory(total, used, free);
    }
}



