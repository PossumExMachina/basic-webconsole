package monitoring.appServer.common;

import monitoring.commands.control.LinuxCommands;
import monitoring.utils.DetectResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstalledSoftwareService {

    @Autowired
    DetectResource detectResource;

    public InstalledSoftwareDTO getInstalledSoftwareDTO(){
       boolean dockerInstalled = detectResource.resourcePresent(LinuxCommands.dockerExists, LinuxCommands.dockerControlOutput);
        return new InstalledSoftwareDTO(dockerInstalled);
    }

}
