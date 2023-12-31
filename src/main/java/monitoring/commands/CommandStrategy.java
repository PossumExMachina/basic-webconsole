package monitoring.commands;

import monitoring.appServer.common.State;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommandStrategy {
    List<String> getFreeMemory();
    State stopServer();
    State startServer();
}
