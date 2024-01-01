package monitoring.commands.control;

import monitoring.appServer.common.State;

public interface ControlStrategy {
    State stopResource();
    State startResource();


}
