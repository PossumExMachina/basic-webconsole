/*
package monitoring.docker;

import monitoring.common.State;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DockerContainerTest {

    @Test
    void testNoArgsConstructor() {
        DockerContainer dockerContainer = new DockerContainer();
        assertNotNull(dockerContainer, "NoArgsConstructor should create a non-null DockerContainer object.");
    }

    @Test
    void testAllArgsConstructor() {
        DockerContainer dockerContainer = new DockerContainer("containerId", "imageName", "createdAt", "RUNNING", "containerName");
        assertNotNull(dockerContainer, "AllArgsConstructor should create a non-null DockerContainer object with properties.");
        assertEquals("containerId", dockerContainer.getID());
        assertEquals("imageName", dockerContainer.getImage());
        assertEquals("createdAt", dockerContainer.getCreatedAt());
        assertEquals("RUNNING", dockerContainer.getState());
        assertEquals("containerName", dockerContainer.getName());
    }

    @Test
    @Disabled
    void testSettersAndGetters() {
        DockerContainer dockerContainer = new DockerContainer();
        dockerContainer.setID("containerId");
        dockerContainer.setImage("imageName");
        dockerContainer.setCreatedAt("createdAt");
        dockerContainer.setState("RUNNING");
       // dockerContainer.setNames("containerName");

        assertEquals("containerId", dockerContainer.getID());
        assertEquals("imageName", dockerContainer.getImage());
        assertEquals("createdAt", dockerContainer.getCreatedAt());
        assertEquals("RUNNING", dockerContainer.getState());
        assertEquals("containerName", dockerContainer.getName());
    }

    @Test
    void testToStringMethod() {
        DockerContainer dockerContainer = new DockerContainer("containerId", "imageName", "createdAt", State.RUNNING, "containerName");
        String expectedString = "DockerContainer(containerID=containerId, image=imageName, created=createdAt, state=RUNNING, name=containerName)";
        assertEquals(expectedString, dockerContainer.toString(), "ToString method should return the correct string representation of DockerContainer object.");
    }
}*/
