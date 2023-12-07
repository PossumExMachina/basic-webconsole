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
import java.util.regex.Pattern;

@Service
public class TomcatService {

    private static final Logger logger = LoggerFactory.getLogger(TomcatService.class);
    private final ProcessBuilderFactory processBuilderFactory;

    @Autowired
    public TomcatService(ProcessBuilderFactory processBuilderFactory) {
        this.processBuilderFactory = processBuilderFactory;
    }



    public boolean isTomcatRunning() {
        String[] command = {"bash", "-c", "ps aux | grep tomcat | grep -v grep"};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }



    public TomcatState stopTomcat() {
        String[] command = {"bash", "-c", "systemctl stop tomcat"};
        return changeTomcatState(command);
    }

    public TomcatState startTomcat(){
        String[] command = {"bash", "-c", "systemctl start tomcat"};
        return changeTomcatState(command);
    }

    public TomcatState changeTomcatState(String[] command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
            return TomcatState.FAILED; // Use FAILED state for exceptions
        }

        // Delay to allow command to take effect
        try {
            Thread.sleep(2000); // Wait for 2 seconds before the first check
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Poll the Tomcat status for a certain duration
        int attempts = 10; // Number of attempts
        for (int i = 0; i < attempts; i++) {
            if (command[2].contains("stop") && !isTomcatRunning()) {
                return TomcatState.STOPPED;
            } else if (command[2].contains("start") && isTomcatRunning()) {
                return TomcatState.RUNNING;
            }
            try {
                Thread.sleep(1000); // Wait for 1 second before the next check
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return command[2].contains("stop") ? TomcatState.STOPPED : TomcatState.RUNNING;
    }



}



