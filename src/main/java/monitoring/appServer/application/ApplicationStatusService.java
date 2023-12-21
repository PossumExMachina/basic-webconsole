package monitoring.appServer.application;
import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.appServer.tomcat.TomcatService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                JSONObject json = urlService.makeHttpRequest(url);
                if (!json.containsValue("UP")) {
                    notRunningApps.add(url);
                }
            }
            catch (ParseException | IOException e) {
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
                JSONObject json = urlService.makeHttpRequest(url);
                if (json.containsValue("UP")) {
                    runningApps.add(url);
                }
            }
            catch (ParseException | IOException e) {
                e.getSuppressed();
            }
        }
        return runningApps;
    }



}
