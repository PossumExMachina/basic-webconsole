package monitoring.docker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
public class DockerController {

    @Autowired
    private DockerContainerService dockerContainerService;

    @Autowired
    private DockerControlService dockerControlService;


    @GetMapping("/dockerInfo")
    public ResponseEntity<String> getResources() throws IOException {
        List<DockerContainer> containers = dockerContainerService.parseDockerJson();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse;

        try {
            jsonResponse = mapper.writeValueAsString(containers);
        } catch (Exception e) {
            jsonResponse = "{\"error\": \"Error converting to JSON.\"}";
        }

        return ResponseEntity.ok(jsonResponse);
    }

    @PostMapping("/docker/{id}")
    public ResponseEntity<?> deleteContainer(@PathVariable String id) throws IOException {
        dockerControlService.deleteContainer(id);
        return ResponseEntity.ok(id + " stopped successfully.");
    }

}
