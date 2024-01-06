package monitoring.docker;

import lombok.*;
import monitoring.appServer.common.State;
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DockerContainer {
    private String containerID;
    private String image;
    private String created;
    private State state;
    private String name;

}

