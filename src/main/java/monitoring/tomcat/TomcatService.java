package monitoring.tomcat;

import monitoring.ProcessBuilderFactory;
import monitoring.tomcat.TomcatStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class TomcatService {

    private static final Logger logger = LoggerFactory.getLogger(TomcatService.class);
    private final ProcessBuilderFactory processBuilderFactory;

    @Autowired
    public TomcatService(ProcessBuilderFactory processBuilderFactory) {
        this.processBuilderFactory = processBuilderFactory;
    }

    public boolean isTomcatRunning() {

        // executes the following command on the server, which checks status of tomcat server
        String[] command = {"systemctl status tomcat"};
        ProcessBuilder processBuilder = processBuilderFactory.createProcessBuilder(command);
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            logger.error("Error starting process to check Tomcat status", e);
            return false;
        }

        // invokes a reader, that reads from the output and checks for a line indicating that tomcat is running,
        // then it returns false if tomcat is stopped and true if it is not
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            boolean isRunning = reader.lines().anyMatch(line -> line.contains("active"));
            process.waitFor();
            return isRunning;
        } catch (IOException e) {
            logger.error("Error reading process output", e);
        } catch (InterruptedException e) {
            logger.error("Process interrupted", e);
            Thread.currentThread().interrupt();
        }
        return false;
    }



    public TomcatState stopTomcat(){
        String[] command = {"systemctl stop tomcat"};
        return changeTomcatState(command);
    }

    public TomcatState startTomcat(){
        String[] command = {"systemctl start tomcat"};
        return changeTomcatState(command);
    }

    private TomcatState changeTomcatState(String[] command) {
        ProcessBuilder processBuilder = processBuilderFactory.createProcessBuilder(command);
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (isTomcatRunning()){
            return TomcatState.STOPPING;
        }
        return TomcatState.STOPPED;
    }



}



