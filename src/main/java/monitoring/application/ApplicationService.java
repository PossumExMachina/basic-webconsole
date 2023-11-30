package monitoring.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ApplicationService {
    private ApplicationStatus applicationStatus;

    @Autowired
    public ApplicationService(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }


    public List<String> getRunningApplications() {
        applicationStatus.isRunning();
        return applicationStatus.getRunningApps();
    }

    public List<String> getNotRunningApplications() {
        applicationStatus.isRunning();
        return applicationStatus.getNotRunningApps();
    }



}
