package monitoring.appServer.application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class URLService {

    @Autowired
    private TomcatWebappScannerService tomcatWebappScannerService;

    public URLService() {
    }

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


    protected JsonNode makeHttpRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String responseBody = br.lines().collect(Collectors.joining());
                    if (responseBody.isEmpty()) {
                        return null;
                    }
                    try {
                        return objectMapper.readTree(responseBody);
                    } catch (JsonProcessingException e) {
                        throw new IOException("Error parsing JSON response", e);
                    }
                }
            } else {
                throw new IOException("Unexpected response code: " + responseCode);
            }
        } finally {
            connection.disconnect();
        }
    }
}
