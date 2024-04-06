package monitoring.docker;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import monitoring.commands.CommandExec;
import monitoring.commands.control.CommandStrategy;
import monitoring.utils.DetectResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DockerContainerService {

    private static final Logger logger = LoggerFactory.getLogger(DockerContainerService.class);

    @Autowired
    DetectResource detectResource;
    @Autowired
    CommandExec commandExec;
    @Autowired
    CommandStrategy commandStrategy;

    private static final String CONTAINER_CACHE_FILE_PATH = "docker_containers_cache.json";

    private final SimpMessagingTemplate messagingTemplate;

    public DockerContainerService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


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

    private void saveStateToFile(List<DockerContainer> containers, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("WRITING NEW VERSION TO " + filePath);
            mapper.writeValue(new File(filePath), containers);
        } catch (IOException e) {
            logger.error("Failed to save Docker state to file", e);
        }
    }


    private List<DockerContainer> loadStateFromFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(new File(filePath), new TypeReference<List<DockerContainer>>() {});
        } catch (IOException e) {
            logger.error("Failed to load Docker containers state from file", e);
            return new ArrayList<>();
        }
    }

    @Scheduled(fixedRate = 5000)
    public void updateAndNotifyIfNecessary() throws IOException {
        List<DockerContainer> currentContainers = parseDockerJson();
        List<DockerContainer> cachedContainers = loadStateFromFile(CONTAINER_CACHE_FILE_PATH);

        if (!currentContainers.equals(cachedContainers)) {
            logger.info("JSON FILES ARE DIFFERENT");
            saveStateToFile(currentContainers, CONTAINER_CACHE_FILE_PATH);
            notifyFrontendOfContainerUpdate(currentContainers);
        }
    }

    private void notifyFrontendOfContainerUpdate(List<DockerContainer> containers) {
        logger.info("FE notified of change");
        messagingTemplate.convertAndSend("/topic/containers", containers);
    }


}
