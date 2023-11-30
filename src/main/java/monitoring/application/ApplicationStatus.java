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


    private static final Logger logger = LoggerFactory.getLogger(TomcatService.class);

    private List<String> runningApps = new ArrayList<>();
    private List<String> notRunningApps = new ArrayList<>();

    public ApplicationStatus() {
    }

    private List<String> getURL() {
        File folder = new File("/opt/prod/webapps");
        String mandatoryFile = "jokrocova";
        File[] directories = folder.listFiles();
        List<String> urlList = new ArrayList<>();

        if (directories != null) {
            for (File dir : directories) {
                File[] subdirs = dir.listFiles();
                if (subdirs != null) {
                    for (File fil : subdirs) {
                        if (fil.getName().contains(mandatoryFile)) {
                            String url = "http://localhost:8080/" + dir.getName() + "/actuator/health";
                            urlList.add(url);
                        }
                    }
                }
            }
        }
        return urlList;
    }

    public void emptyAppsList(){
        runningApps.clear();
        notRunningApps.clear();
    }


    public void isRunning() {
        emptyAppsList();

        List<String> urlList = List.of( // TODO: replace with actual URLS that will be used
                "https://jsonplaceholder.typicode.com/users/1/",
                "https://jsonplaceholder.typicode.com/users/2/"
        );
        JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);

        for (String url : urlList) {
            try {
                URL urlToTry = new URL(url);
                JSONObject json = (JSONObject) parser.parse(new InputStreamReader(urlToTry.openStream()));
                if (json.getAsNumber("id").intValue() == 1) {
                    runningApps.add(url);
                } else {
                    notRunningApps.add(url);
                }
            } catch (ParseException | IOException e) {
                notRunningApps.add(url);
                logger.error("Error checking application status for: {}", url, e);
            }
        }
    }

    public List<String> getRunningApps() {
        return runningApps;
    }

    public List<String> getNotRunningApps() {
        return notRunningApps;
    }
}
