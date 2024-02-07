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

    /**
     Returns healtcheck URL for SpringBoot application.
     @param applName - name of the application for health check probe
     @return actuator/health url (String)
     */
    public String createURL(String applName) {
        return "http://localhost:8080/" + applName + "/actuator/health";
    }

    /**
     * Makes an HTTP GET request to the specified URL and returns the response body as a JsonNode.
     *
     * @param urlString The URL to make the HTTP request to.
     * @return A JsonNode representing the response body.
     * @throws IOException If there is an error in making the HTTP request or processing the response.
     */
    JsonNode makeHttpRequest(String urlString) throws IOException {
        HttpURLConnection connection = createHttpURLConnection(urlString);
        try {
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String responseBody = br.lines().collect(Collectors.joining());
                    if (responseBody.isEmpty()) {
                        throw new IOException("Response body is empty");
                    }
                    return objectMapper.readTree(responseBody);
                } catch (JsonProcessingException e) {
                    throw new IOException("Error parsing JSON response", e);
                }
            } else {
                throw new IOException("Unexpected response code: " + responseCode);
            }
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Creates and returns an HttpURLConnection for the specified URL.
     *
     * @param urlString The URL for which an HttpURLConnection is to be created.
     * @return An HttpURLConnection for the specified URL.
     * @throws IOException If there is an error in creating the HttpURLConnection.
     */
    HttpURLConnection createHttpURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString); //TODO: Rewrite this (deprecated)
        return (HttpURLConnection) url.openConnection();
    }

}
