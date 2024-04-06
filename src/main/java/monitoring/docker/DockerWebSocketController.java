package monitoring.docker;

import monitoring.docker.DockerContainer;
import monitoring.docker.DockerContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.List;

@Controller
public class DockerWebSocketController {

    @Autowired
    private DockerContainerService dockerContainerService;

    @MessageMapping("/update")
    @SendTo("/topic/containers")
    @CrossOrigin("http://localhost:4200")
    public List<DockerContainer> getDockerContainersUpdate() throws IOException {
        return dockerContainerService.parseDockerJson();
    }
}
