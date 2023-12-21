package monitoring.appServer.application;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minidev.json.parser.JSONParser;

@Service
public class URLService {

    @Autowired
    private TomcatWebappScannerService tomcatWebappScannerService;

    JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);

    public List<String> getURL() {
        /*
        iterates over application names and creates standard spring boot health check url for each app name
        returns list of created url
        */
        List<String> urlList = new ArrayList<>();
        tomcatWebappScannerService.scanForWebapps().ifPresent(appNames -> {
            for (String name : appNames) {
                String url = "http://localhost:8080/" + name + "/actuator/health";
                urlList.add(url);
            }
        });
        return urlList;
    }

    protected JSONObject makeHttpRequest(String url) throws IOException, ParseException {
        /*
        makes http request to given url and returns parsed json object
            Args: url that should be tried

        */
        URL urlToTry = new URL(url);
        return (JSONObject) parser.parse(new InputStreamReader(urlToTry.openStream()));
    }
}
