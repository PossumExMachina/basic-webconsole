package monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommandExec {

    public List<String> executeCommand(String command) throws IOException {List<String> output = new ArrayList<>();
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        Process process;
        try {
            process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    throw new IOException("Command exited with error code: " + exitCode);
                }

                String s;
                while((s = reader.readLine()) != null)
                {
                    output.add(s + "\n");
                }
                return output;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted while waiting for the command to complete", e);
            }
        } catch (IOException e) {
            throw new IOException("Error starting the process for command: " + command, e);
        }
    }


}
