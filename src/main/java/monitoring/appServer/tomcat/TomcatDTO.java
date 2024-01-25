package monitoring.appServer.tomcat;

import lombok.Getter;
import monitoring.appServer.application.Application;
import monitoring.common.State;

import java.util.List;

@Getter
public class TomcatDTO {

    private final List<Application> applications;
    private final State tomcatState;

    public TomcatDTO(List<Application> applications, String tomcatState) {
        this.tomcatState = State.valueOf(tomcatState);
        this.applications = applications;
    }

}
