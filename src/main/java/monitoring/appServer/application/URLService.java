package monitoring.appServer.application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class URLService {

    @Autowired
    public ApplicationService applicationService;


    // creates urls for healthcheck probe
    public List<String> getURL(){
        List<String> urlList = new ArrayList<>();
        for (String name : applicationService.getApplicationName()){
            String url = "http://localhost:8080/" + name + "/actuator/health";
            urlList.add(url);
        }
        return urlList;
    }




}
