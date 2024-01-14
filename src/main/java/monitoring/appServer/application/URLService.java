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
import java.util.stream.Collectors;


@Service
public class URLService {

    @Autowired
    private TomcatWebappScannerService tomcatWebappScannerService;

    public URLService() {
    }

    /**
     Returns healtcheck URL for SpringBoot application.
     @param applName - name of the application for health check probe
     @return actuator/health url (String)
     */
    public String createURL(String applName) {
        String url = "http://localhost:8080/" + applName + "/actuator/health";
        return url;
    }

    /**
     * Makes an HTTP request to the specified URL and returns the response as a JsonNode.
     *
     * This method establishes an HTTP connection to the given URL and retrieves the response.
     * It expects the response to be in JSON format. If the HTTP response code is OK (200),
     * it reads the response body and converts it into a JsonNode. If the response body is empty,
     * it returns null. In case of any issues in parsing the JSON response, an IOException is thrown.
     * An IOException is also thrown if the response code is not HTTP_OK.
     *
     * @param urlString The URL string to which the HTTP request is made.
     * @return JsonNode The parsed JSON response as a JsonNode. Returns null if the response body is empty.
     * @throws IOException If there's an issue with the network connection, if the server returns
     *                     an unexpected response code, or if there's an error parsing the JSON response.
     */
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
