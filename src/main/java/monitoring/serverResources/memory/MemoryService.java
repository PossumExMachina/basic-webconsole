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

     * Splits the line into parts based on whitespace and assigns specific parts to the total, used, and free memory fields
     * of the Memory object. Assumes a specific format for the input line.
     *
     * @param line A string representing a single line of memory usage output.
     * @return Memory A Memory object populated with values parsed from the line.
     */
    private Memory parseLine(String line) {
        String[] parts = line.split("\\s+");

        String total = parts[0];
        String used = parts[2];
        String free = parts[4];

        return new Memory(total, used, free);
    }
}



