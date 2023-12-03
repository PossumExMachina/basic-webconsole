package monitoring.application;

import monitoring.tomcat.TomcatService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
