package monitoring.commands;

import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.appServer.common.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LinuxCommandsTest {

    @Mock
    private CommandExec mockCommandExec;

    @Mock
    TomcatCommandService mocktomcatCommandService;

    @InjectMocks
    private LinuxCommands linuxCommands;

    @BeforeEach
    void setUp() {

        mockCommandExec = Mockito.mock(CommandExec.class);
        mocktomcatCommandService = Mockito.mock(TomcatCommandService.class);
        linuxCommands = new LinuxCommands();
        ReflectionTestUtils.setField(linuxCommands, "commandExec", mockCommandExec);
        ReflectionTestUtils.setField(linuxCommands, "tomcatCommandService", mocktomcatCommandService);

    }


    @Test
    public void testGetMemory() throws IOException {
        List<String> mockOutput = Arrays.asList("Sample free memory output", "Sample values");
        when(mockCommandExec.executeCommand(anyString())).thenReturn(mockOutput);

        List<String> result = linuxCommands.getFreeMemory();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Sample free memory output", result.get(0));
    }



    @Test
    public void testGetMemoryThrowsExceptionWhenEmptyOutput() throws IOException {
        when(mockCommandExec.executeCommand("df -h")).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> linuxCommands.getFreeMemory());
    }

    @Test
    public void mockStartServer(){
        String[] command = {"bash", "-c", "systemctl start tomcat"};
        State expectedState = State.RUNNING;
        when(mocktomcatCommandService.changeTomcatState(command)).thenReturn(expectedState);
        State actualState = linuxCommands.startServer();
        assertEquals(expectedState, actualState);
    }

    @Test
    public void mockStartServer_whenCommandIsWrong(){
        String[] command = {"bash", "-c", "systemctl xxx tomcat"};
        State expectedState = State.RUNNING;
        when(mocktomcatCommandService.changeTomcatState(command)).thenReturn(expectedState);
        State actualState = linuxCommands.startServer();
        assertNotEquals(expectedState, actualState);
    }

    @Test
    public void mockStopServer(){
        String[] command = {"bash", "-c", "systemctl stop tomcat"};
        State expectedState = State.STOPPED;
        when(mocktomcatCommandService.changeTomcatState(command)).thenReturn(expectedState);
        State actualState = linuxCommands.stopServer();
        assertEquals(expectedState, actualState);
    }




}