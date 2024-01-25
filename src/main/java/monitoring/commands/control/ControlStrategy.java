package monitoring.commands.control;

import monitoring.common.ResourceContext;
import monitoring.common.State;
import org.springframework.stereotype.Component;

@Component
public interface ControlStrategy {
    String stopResource(ResourceContext resourceContext);
    String startResource(ResourceContext resourceContext);

}
