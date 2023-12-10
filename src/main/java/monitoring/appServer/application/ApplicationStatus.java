package monitoring.appServer.application;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

public class ApplicationStatus {


    private final List<String> runningApps = new ArrayList<>();
    private final List<String> notRunningApps = new ArrayList<>();

    public ApplicationStatus() {
    }

    public List<String> getRunningApps() {
        return runningApps;
    }

    public List<String> getNotRunningApps() {
        return notRunningApps;
    }
}
