package monitoring.appServer.application;

import lombok.*;
import monitoring.appServer.common.State;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Application {

    private String name;
    private State state;



}
