package monitoring.commands;

import monitoring.appServer.tomcat.TomcatState;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public interface CommandStrategy {
    List<String> getFreeMemory();
    TomcatState stopServer();
    TomcatState startServer();
}
