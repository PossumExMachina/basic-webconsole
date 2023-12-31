package monitoring.utils;

import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.commands.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class DetectResource {

    private static final Logger logger = LoggerFactory.getLogger(TomcatCommandService.class);



    public boolean resourcePresent(String command, String controlOutput) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(controlOutput)) {
                    logger.info("Docker found: {}", line);
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (process != null) {
                process.destroy();
                logger.info("Destroyed checking process.");
            }
        }
        return false;
    }

}