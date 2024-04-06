package monitoring.serverResources.disk;

import monitoring.serverResources.memory.Memory;
import monitoring.serverResources.memory.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DiskController {

    @Autowired
    private DiskService diskService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/diskInfo")
    public ResponseEntity<List<DiskUsage>> getDiskUsage() throws IOException {
        return ResponseEntity.ok(diskService.getDiskUsage());
    }
}
