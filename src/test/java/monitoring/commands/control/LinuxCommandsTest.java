package monitoring.commands.control;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LinuxCommandsTest {

    private LinuxCommands linuxCommands;

    @Before
    public void setUp() {
        linuxCommands = new LinuxCommands();
    }

    @Test
    public void testGetDockerInstalledCmd() {
        assertEquals("docker -v", linuxCommands.getDockerInstalledCmd());
    }

    @Test
    public void testGetFreeMemoryCmd() {
        assertEquals("free -m", linuxCommands.getFreeMemoryCmd());
    }

    @Test
    public void testGetListContainersCmd() {
        assertEquals("docker ps -a", linuxCommands.getListContainersCmd());
    }

    @Test
    public void testGetStartTomcatCmd() {
        assertEquals("sudo systemctl start tomcat", linuxCommands.getStartTomcatCmd());
    }

    @Test
    public void testGetStopTomcatCmd() {
        assertEquals("sudo systemctl stop tomcat", linuxCommands.getStopTomcatCmd());
    }

    @Test
    public void testGetTomcatInstalledCmd() {
        assertEquals("ls" + System.getenv("CATALINA_HOME"), linuxCommands.getTomcatInstalledCmd());
    }

    @Test
    public void testGetTomcatControlOutput() {
        assertEquals("webapps", linuxCommands.getTomcatControlOutput());
    }

    @Test
    public void testGetDiskUsageCmd() {
        assertEquals("df -h", linuxCommands.getDiskUsageCmd());
    }

    @Test
    public void testGetDockerControlOutput() {
        assertEquals("Docker version", linuxCommands.getDockeControlOutput());
    }
}
