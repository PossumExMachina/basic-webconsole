package monitoring.appServer.application;

import com.fasterxml.jackson.databind.JsonNode;
import monitoring.appServer.common.State;
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
