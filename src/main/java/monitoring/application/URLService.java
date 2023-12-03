package monitoring.application;
import java.util.ArrayList;
import java.util.List;

public class URLService {
    ApplicationService applicationService;


    // creates urls for healthcheck probe
    List<String> getURL(){
        List<String> urlList = new ArrayList<>();
        for (String name : applicationService.getApplicationName()){
            String url = "http://localhost:8080/" + name + "/actuator/health";
            urlList.add(url);
        }
        return urlList;
    }




}
