package monitoring.docker;

import monitoring.common.State;
import monitoring.commands.CommandExec;
import monitoring.commands.control.CommandStrategy;
import monitoring.utils.DetectResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DockerContainerService {

    private static final Logger logger = LoggerFactory.getLogger(DockerContainerService.class);

    @Autowired
    DetectResource detectResource;
    @Autowired
    CommandExec commandExec;
    @Autowired
    CommandStrategy commandStrategy;

    /**
     * Retrieves the IDs of all available Docker containers.
     *
     * Collects and returns a list of IDs from all Docker containers obtained
     * through the getDockerContainers method.
     *
     * @return List<String> A list of Docker container IDs.
     * @throws IOException If an I/O error occurs during the process.
     */
    public List<String> getContainerID() throws IOException {
        List<String> IDlist = new ArrayList<>();
        for (DockerContainer container : getDockerContainers()){
            IDlist.add( container.getContainerID());
        }
        return IDlist;
    }


    /**
     * Retrieves a list of Docker containers present on the system.
     *
     * First, checks if Docker is installed on the system. If not, it throws a FileNotFoundException.
     * Then, executes a command to list Docker containers and parses the output into a list of DockerContainer objects.
     * If the command execution does not produce any output, an IOException is thrown.
     *
     * @return List<DockerContainer> A list of DockerContainer objects representing the containers on the system.
     * @throws FileNotFoundException If Docker is not installed on the system.
     * @throws IOException If an I/O error occurs or if the command to list containers fails to produce output.
     */
    public List<DockerContainer> getDockerContainers() throws IOException {
        if (!detectResource.resourcePresent(commandStrategy.getDockerInstalledCmd(), commandStrategy.getDockeControlOutput())) {
            logger.info("Docker is not present on the system");
        }

        List<String> outputLines = commandExec.executeCommand(commandStrategy.getListContainersCmd());
        if (outputLines == null || outputLines.isEmpty()) {
            throw new IOException("Failed to list containers: No output from command");
        }

        return outputLines.stream()
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }



    /**
     * Parses a line of output representing a Docker container and constructs a DockerContainer object.
     *
     * The method expects a formatted string containing Docker container details and splits it into parts.
     * It then extracts and constructs the necessary fields to create a DockerContainer object.
     *
     * @param line A string representing a single line of output for a Docker container.
     * @return DockerContainer An object representing the parsed Docker container.
     */
    private DockerContainer parseLine(String line) {
        String[] parts = line.split("\\s+");
        String containerID = parts[0];
        String image = parts[1];
        String created = parts[3] + " " + parts[4] + " " + parts[5];
        State status = parseStatus(parts[8]);
        String names = parts[parts.length - 1];

        return new DockerContainer(containerID, image, created, status, names);
    }

    /**
     * Parses a string representing the status of a Docker container and returns the corresponding State enum.
     *
     * @param statusString The string representation of a Docker container's status.
     * @return State The parsed state of the Docker container.
     */
    private State parseStatus(String statusString) {
        return State.valueOf(statusString.toUpperCase());
    }
}
