package monitoring.docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ContainerRepository {


    @Autowired
    DockerContainerService dockerContainerService;

    public DockerContainer getContainerByID(String id) throws IOException {
        return dockerContainerService.getDockerContainers().stream()
                .filter(container -> container.getContainerID().equals(id))
                .findFirst()
                .orElse(null); // Return null if no container is found
    }



}
