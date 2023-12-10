package monitoring.appServer.application;


import monitoring.appServer.tomcat.TomcatService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationStatus applicationStatus;
    private static final Logger logger = LoggerFactory.getLogger(TomcatService.class);
    JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);


    public ApplicationService() {
    }



    // method to empty lists every time isRunning method runs
    public void emptyAppsList(){
        applicationStatus.getRunningApps().clear();
        applicationStatus.getNotRunningApps().clear();
    }

    protected JSONObject makeHttpRequest(String url) throws IOException, ParseException {
        URL urlToTry = new URL(url);
        return (JSONObject) parser.parse(new InputStreamReader(urlToTry.openStream()));
    }


    public void getRunningAndNotRunningApps() {
        emptyAppsList();

        List<String> urlList = List.of( // TODO: replace with actual URLS that will be used
                "https://jsonplaceholder.typicode.com/users/1/",
                "https://jsonplaceholder.typicode.com/users/2/"
        );

        // for each URL, this calls healthcheck probe and puts running apps into runningApps list and not running apps
        // into notRunningApps list.
        for (String url : urlList) {
                try {
                    JSONObject json = makeHttpRequest(url);
                    if (json.getAsNumber("id").intValue() == 1) {
                        applicationStatus.getRunningApps().add(url);
                    } else {
                        applicationStatus.getNotRunningApps().add(url);
                    }
                } catch (ParseException | IOException e) {
                    applicationStatus.getNotRunningApps().add(url);
                    logger.error("Error checking application status for: {}", url, e);
                }
        }
    }

    public List<String> getApplicationName(){
        File folder = new File("/opt/prod/webapps");

        String mandatoryFile = "jokrocova"; // mandatory file is used to check if the directory is app directory
        File[] directories = folder.listFiles();
        List<String> nameList = new ArrayList<>();

        if (directories != null) {
            for (File dir : directories) {
                File[] subdirs = dir.listFiles();
                if (subdirs != null) {
                    for (File fil : subdirs) {
                        if (fil.getName().contains(mandatoryFile)) {
                            nameList.add(fil.getName());
                        }
                    }
                }
            }
        }
        return nameList;
    }

  // list containing running applications
    public List<String> getRunningApplications() {
        getRunningAndNotRunningApps();
        return applicationStatus.getRunningApps();
    }


    //list containing stopped applications
    public List<String> getNotRunningApplications() {
        getRunningAndNotRunningApps();
        return applicationStatus.getNotRunningApps();
    }






}
