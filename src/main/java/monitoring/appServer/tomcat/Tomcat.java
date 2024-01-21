package monitoring.appServer.tomcat;

import lombok.*;
import monitoring.common.State;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@ToString
public class Tomcat {
   private State state;
}
