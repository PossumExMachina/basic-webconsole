package monitoring.appServer.common;

import monitoring.commands.Commands;
import monitoring.utils.DetectResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstalledSoftwareService {

    @Autowired
    DetectResource detectResource;

    public InstalledSoftwareDTO getInstalledSoftwareDTO(){
       boolean dockerInstalled = detectResource.resourcePresent(Commands.dockerExists, Commands.dockerControlOutput);
        return new InstalledSoftwareDTO(dockerInstalled);
    }

}
