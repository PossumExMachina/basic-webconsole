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
            logger.error("IO Exception", e);
        } finally {
            if (process != null) {
                process.destroy();
            }
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



