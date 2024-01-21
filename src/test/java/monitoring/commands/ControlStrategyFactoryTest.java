package monitoring.commands;

import monitoring.appServer.application.URLService;
import monitoring.appServer.tomcat.TomcatControlService;
import monitoring.commands.control.ControlStrategy;
import monitoring.docker.DockerContainerService;
import monitoring.docker.DockerControlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import javax.naming.directory.InvalidAttributeValueException;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ControlStrategyFactoryTest {

    @Mock
    private TomcatControlService mockTomcatControlService;

    @Mock
    private DockerControlService mockDockerControlService;

    @Mock
    private DockerContainerService mockDockerContainerService;

    @InjectMocks
    private ControlStrategyFactory controlStrategyFactory;

    @BeforeEach
    void setUp() {
        controlStrategyFactory = new ControlStrategyFactory();
        mockDockerContainerService = Mockito.mock(DockerContainerService.class);
        mockDockerControlService = Mockito.mock(DockerControlService.class);
        mockTomcatControlService =  Mockito.mock(TomcatControlService.class);

        ReflectionTestUtils.setField(controlStrategyFactory, "dockerContainerService", mockDockerContainerService);
        ReflectionTestUtils.setField(controlStrategyFactory, "dockerControlService", mockDockerControlService);
        ReflectionTestUtils.setField(controlStrategyFactory, "tomcatControlService", mockTomcatControlService);
    }


    @Test
    void ifResourceTomcatThenReturnTomcatControlService() throws IOException, InvalidAttributeValueException {

        String resourceType = "tomcat";
        ControlStrategy result = controlStrategyFactory.getStrategy(resourceType);

        assertTrue(result instanceof TomcatControlService, "Expecting TomcatControlService to be returned for 'tomcat' resource type.");
    }

    @Test
    void ifResourceInvalidThenReturnInvalidAttributeValueExc() throws IOException, InvalidAttributeValueException {
        String resourceType = "invalid";
        assertThrows(InvalidAttributeValueException.class, () -> controlStrategyFactory.getStrategy(resourceType));
    }


    @Test
    void ifResourceDockerContainerIdThenReturnDockerControlService() throws IOException, InvalidAttributeValueException {

        String dockerId = "docker123";
        when(mockDockerContainerService.getContainerID()).thenReturn(Collections.singletonList(dockerId));

        ControlStrategy result = controlStrategyFactory.getStrategy(dockerId);

        assertTrue(result instanceof DockerControlService, "Expected DockerControlService to be returned for Docker container ID resource type.");
    }


}