package monitoring.appServer.application;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationStatusService {

    @Autowired
    private URLService urlService;

    public ApplicationStatusService() {
    }

    public List<String> getNotRunningApps(){
        /*
         Makes http requests to application healthcheck urls and adds not running applications to notRunningApps list
        */
        List<String> urlList = urlService.getURL();
        List<String> notRunningApps = new ArrayList<>();

        for (String url : urlList) {
            try {
                JsonNode json = urlService.makeHttpRequest(url);
                String status = json.get("status").toString().toUpperCase(); // Convert to upper case
                if (!"UP".equals(status)) {
                    notRunningApps.add(url);
                }
            }
            catch ( IOException e) {
                notRunningApps.add(url);
            }
        }
        return notRunningApps;
    }

    public List<String> getRunningApps(){
        /*
         Makes http requests to application healthcheck urls and adds  running applications to runningApps list
        */
        List<String> urlList = urlService.getURL();
        List<String> runningApps = new ArrayList<>();

        for (String url : urlList) {
            try {
                JsonNode json = urlService.makeHttpRequest(url);
                String status = json.get("status").toString().toUpperCase(); // Convert to upper case
                if ("UP".equals(status)) {
                    runningApps.add(url);
                }
            }
            catch (IOException e) {
                e.getSuppressed();
            }
        }
        return runningApps;
    }



}
