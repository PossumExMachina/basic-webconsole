package monitoring.commands.control;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class macOSCommandsTest {

    private macOSCommands macOSCommands;

    @Before
    public void setUp() {
        macOSCommands = new macOSCommands();
    }

    @Test
    public void testGetDockerInstalledCmd() {
        assertEquals("docker -v", macOSCommands.getDockerInstalledCmd());
    }

    @Test
    public void testGetFreeMemoryCmd() {
        assertEquals("""
            vm_stat | awk -v FS="[^0-9]+" '
                /Pages free/ {free=$2}
                /Pages active/ {active=$2}
                /Pages inactive/ {inactive=$2}
                /Pages speculative/ {speculative=$2}
                /Pages wired down/ {wired=$2}
                END {
                    used=(active+inactive+wired)*4096/1048576;
                    total=(free+active+inactive+speculative+wired)*4096/1048576;
                    free=free*4096/1048576;
                    printf "Total\\tUsed\\tFree\\n";
                    printf "%d MB\\t%d MB\\t%d MB\\n", total, used, free
                }'
                """, macOSCommands.getFreeMemoryCmd());
    }

    @Test
    public void testGetListContainersCmd() {
        assertEquals("docker ps -a", macOSCommands.getListContainersCmd());
    }

    @Test
    public void testGetStartTomcatCmd() {
        assertEquals("sudo systemctl start tomcat", macOSCommands.getStartTomcatCmd());
    }

    @Test
    public void testGetStopTomcatCmd() {
        assertEquals("sudo systemctl stop tomcat", macOSCommands.getStopTomcatCmd());
    }

    @Test
    public void testGetTomcatInstalledCmd() {
        assertEquals("ls " + System.getenv("CATALINA_HOME"), macOSCommands.getTomcatInstalledCmd());
    }

    @Test
    public void testGetTomcatControlOutput() {
        assertEquals("webapps", macOSCommands.getTomcatControlOutput());
    }

    @Test
    public void testGetDiskUsageCmd() {
        assertEquals("echo \"Filesystem Size Used Avail Use% Mounted on\"; df -h | awk 'NR>1 {print $1, $2, $3, $4, $5, $9}'", macOSCommands.getDiskUsageCmd());
    }

    @Test
    public void testGetDockerControlOutput() {
        assertEquals("Docker version", macOSCommands.getDockeControlOutput());
    }
}
