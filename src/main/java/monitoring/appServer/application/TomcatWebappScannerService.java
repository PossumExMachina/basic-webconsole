package monitoring.appServer.application;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public interface TomcatWebappScannerService {
    Optional<List<String>> scanForWebapps();

}
