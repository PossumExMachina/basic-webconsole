package monitoring.serverResources.memory;

import monitoring.commands.CommandExec;
import monitoring.commands.CommandStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemoryService {

    @Autowired
    private CommandStrategy commandStrategy;

    @Autowired
    CommandExec commandExec;

    public MemoryService() {

    }

    public List<String> getFreeMemory() {
        return commandStrategy.getFreeMemory();
    }
}



