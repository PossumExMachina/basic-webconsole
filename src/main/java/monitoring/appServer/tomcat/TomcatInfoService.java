package monitoring.appServer.tomcat;

import monitoring.appServer.application.Application;
import monitoring.appServer.application.ApplicationService;
import monitoring.common.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TomcatInfoService {

    @Autowired
    private TomcatService tomcatService;

    @Autowired
    ApplicationService applicationService;


    public TomcatDTO getTomcatStatus() throws IOException {
        State tomcatState = tomcatService.getTomcatState();
        List<Application> applications = applicationService.getApplications();
        return new TomcatDTO(applications, tomcatState);
    }

}
