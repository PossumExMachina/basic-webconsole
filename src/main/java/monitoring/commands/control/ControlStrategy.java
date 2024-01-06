package monitoring.commands.control;

import monitoring.appServer.common.ResourceContext;
import monitoring.appServer.common.State;
import org.springframework.stereotype.Component;

@Component
public interface ControlStrategy {
    State stopResource(ResourceContext resourceContext);
    State startResource(ResourceContext resourceContext);

}
