package monitoring.appServer.common;

import lombok.Getter;

@Getter
public class InstalledSoftwareDTO {

    private final boolean dockerInstalled;

    public InstalledSoftwareDTO(boolean dockerInstalled) {
        this.dockerInstalled = dockerInstalled;

    }


}
