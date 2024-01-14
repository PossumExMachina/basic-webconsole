package monitoring.commands;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommandExec {



    /**
     * Executes a given command in the bash shell and returns its output.
     *
     * @param command The command to be executed.
     * @return A list of strings, each representing a line of the command's output.
     * @throws IOException If an I/O error occurs during command execution.
     */
    public List<String> executeCommand(String command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        Process process = processBuilder.start();
        return readProcessOutput(process);
    }


    /**
     * Reads and returns the output of a given process.
     *
     * @param process The process whose output is to be read.
     * @return A list of strings, each representing a line of the process's output.
     * @throws IOException If an I/O error occurs during reading the process output or if the process exits with an error.
     */
    private List<String> readProcessOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            List<String> output = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line + "\n");
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Command exited with error code: " + exitCode);
            }
            return output;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while reading the command output", e);
        }
    }



}
