package monitoring.commands;

import monitoring.appServer.tomcat.TomcatState;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CommandStrategy {
    List<String> getFreeMemory();
    TomcatState stopServer();
    TomcatState startServer();
}
