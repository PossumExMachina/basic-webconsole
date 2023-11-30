package monitoring.tomcat;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Component
public class TomcatStatus {

    private boolean isTomcatRunning;

    public TomcatStatus() {
    }

    public TomcatStatus(boolean isTomcatRunning) {
        this.isTomcatRunning = isTomcatRunning;
    }

    public boolean isTomcatRunning() {
        return isTomcatRunning;
    }
}
