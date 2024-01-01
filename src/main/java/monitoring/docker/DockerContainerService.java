package monitoring.docker;

import monitoring.appServer.common.State;
import monitoring.commands.CommandExec;
import monitoring.commands.Commands;
import monitoring.utils.DetectResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DockerContainerService {

    private static final Logger logger = LoggerFactory.getLogger(DockerContainerService.class);

    @Autowired
    DetectResource detectResource;
    @Autowired
    CommandExec commandExec;

    public List<DockerContainer> getDockerContainers() throws IOException {
        if (!detectResource.resourcePresent(Commands.dockerExists, Commands.dockerControlOutput)) {
            throw new FileNotFoundException("Docker is not present on the system");
        }

        List<String> outputLines = commandExec.executeCommand(Commands.listContainers);
        if (outputLines == null || outputLines.isEmpty()) {
            throw new IOException("Failed to list containers: No output from command");
        }

        return outputLines.stream()
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private DockerContainer parseLine(String line) {
        String[] parts = line.split("\\s+");
        StringBuilder createdBuilder = new StringBuilder();
        String containerID = parts[0];
        String image = parts[1];
        createdBuilder.append(parts[3]).append(" ").append(parts[4]).append(" ").append(parts[5]);
        String created = createdBuilder.toString();
        State status = parseStatus(parts[6]);
        String names = parts[parts.length - 1];

        return new DockerContainer(containerID, image, created, status, names);
    }

    private State parseStatus(String statusString) {
        return State.valueOf(statusString.toUpperCase());
    }
}
