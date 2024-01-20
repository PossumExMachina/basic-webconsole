package monitoring.commands.control;

import monitoring.common.ResourceContext;
import monitoring.common.State;
import org.springframework.stereotype.Component;

@Component
public interface ControlStrategy {
    State stopResource(ResourceContext resourceContext);
    State startResource(ResourceContext resourceContext);

}
