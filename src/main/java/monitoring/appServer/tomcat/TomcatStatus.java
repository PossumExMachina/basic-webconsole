package monitoring.appServer.tomcat;
import org.springframework.stereotype.Component;

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
