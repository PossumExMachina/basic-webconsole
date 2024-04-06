package monitoring.serverResources.memory;

import com.fasterxml.jackson.databind.ObjectMapper;
import monitoring.docker.DockerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MemoryController {

    @Autowired
    private MemoryService memoryService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/memoryInfo")
    public ResponseEntity<List<Memory>> getMemory() throws IOException {

        return ResponseEntity.ok(memoryService.getFreeMemory());
    }
}
