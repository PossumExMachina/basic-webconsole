package monitoring.appServer.application;

import com.fasterxml.jackson.databind.JsonNode;
import monitoring.common.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    private TomcatWebappScannerService tomcatWebappScannerService;

    @Autowired
    private URLService urlService;


    /**
     * Retrieves list of Applications with information about its state (RUNNING / STOPPED / UNKNOWN)
     *
     * This method calls the scanForWebapps method to check for deployed applications.
     * It then iterates through these applications, obtaining the status for each one.
     * In case of an IOException, the application status is set to UNKNOWN.
     * If no applications are found, an empty list is returned.
     *
     * @return List<Application> A list of Application objects, each containing the name and status.
     * If there are no applications deployed, returns an empty list.
     */
    public List<Application> getApplications() {
        Optional<List<String>> optionalAppNames = tomcatWebappScannerService.scanForWebapps();

        if (optionalAppNames.isPresent()) {
            List<String> appNames = optionalAppNames.get();
            return appNames.stream()
                    .map(appName -> {
                        try {
                            State state = getApplicationStatus(appName);
                            return new Application(appName, state);
                        } catch (IOException e) {
                            return new Application(appName, State.UNKNOWN);
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


    /**
     * Determines the status of an application by its name.
     *
     * This method constructs a URL for the application and makes an HTTP request to retrieve its status.
     * If the status is "UP", it returns RUNNING, otherwise it returns STOPPED.
     *
     * @param appName The name of the application whose status is to be determined.
     * @return State (either RUNNING / STOPPED / UNKNOWN.
     * @throws IOException If an error occurs while making the HTTP request.
     */
    private State getApplicationStatus(String appName) throws IOException {
        String url = urlService.createURL(appName);
        try {
            logger.info("trying " + url);
            JsonNode json = urlService.makeHttpRequest(url);
            String status = json.get("status").toString().toUpperCase();
            if ("UP".equals(status)) {
              return State.RUNNING;
            }
            else return State.STOPPED;
        }
        catch ( IOException e) {
           return State.STOPPED;
        }
    }



}
