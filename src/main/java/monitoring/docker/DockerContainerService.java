package monitoring.docker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        for (DockerContainer container : parseDockerJson()){
            IDlist.add( container.getID());
        }
        return IDlist;
    }


    public List<DockerContainer> parseDockerJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<String> jsonStrings = commandExec.executeCommand(commandStrategy.getListContainersCmd());
        String jsonArrayString = "[" + String.join(",", jsonStrings) + "]";

        try {
            return mapper.readValue(jsonArrayString, new TypeReference<List<DockerContainer>>() {});
        } catch (Exception e) {
            logger.error("Error parsing docker from json ", e);
            return null;
        }
    }

}
