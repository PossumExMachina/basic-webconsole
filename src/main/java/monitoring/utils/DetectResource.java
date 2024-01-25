package monitoring.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class DetectResource {

    private static final Logger logger = LoggerFactory.getLogger(DetectResource.class);

    public boolean resourcePresent(String command, String controlOutput) {
        Process process = null;
        try {
            logger.info("executing: " + command);
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                logger.info(line);
                if (line.contains(controlOutput)) {
                    logger.info("Resource found: {}", line);
                    return true;
                }
            }
        } catch (IOException e) {
            logger.error("Could not execute checking command " + command);
        } finally {
            if (process != null) {
                process.destroy();
                logger.info("Destroyed checking process.");
            }
        }
        return false;
    }

}