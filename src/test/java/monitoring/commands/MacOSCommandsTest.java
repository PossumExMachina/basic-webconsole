//package monitoring.commands;
//
//
//import monitoring.appServer.common.State;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//class MacOSCommandsTest {
//
//
//    @Mock
//    private CommandExec mockCommandExec;
//
//    @Mock
//    TomcatCommandService mocktomcatCommandService;
//
//    @InjectMocks
//    private MacOSCommands macOSCommands;
//
//    @BeforeEach
//    void setUp() {
//
//        mockCommandExec = Mockito.mock(CommandExec.class);
//        mocktomcatCommandService = Mockito.mock(TomcatCommandService.class);
//        macOSCommands = new MacOSCommands();
//        ReflectionTestUtils.setField(macOSCommands, "commandExec", mockCommandExec);
//        ReflectionTestUtils.setField(macOSCommands, "tomcatCommandService", mocktomcatCommandService);
//    }
//
//
//    @Test
//    public void testGetMemory() throws IOException {
//        List<String> mockOutput = Arrays.asList("Sample free memory output", "Sample values");
//        when(mockCommandExec.executeCommand(anyString())).thenReturn(mockOutput);
//
//        List<String> result = macOSCommands.getFreeMemory();
//
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        assertEquals("Sample free memory output", result.get(0));
//        assertEquals("Sample values", result.get(1));
//    }
//
//
//
//    @Test
//    public void testGetMemoryThrowsExceptionWhenEmptyOutput() throws IOException {
//        when(mockCommandExec.executeCommand("df -h")).thenReturn(Collections.emptyList());
//
//        assertThrows(RuntimeException.class, () -> macOSCommands.getFreeMemory());
//    }
//
//
//    @Test
//    public void mockStartServer(){
//        String[] command = {"bash", "-c", "sudo /opt/tomcat/apache-tomcat-10.1.17/bin/startup.sh"};
//        State expectedState = State.RUNNING;
//        when(mocktomcatCommandService.changeTomcatState(command)).thenReturn(expectedState);
//        State actualState = macOSCommands.startServer();
//        assertEquals(expectedState, actualState);
//    }
//
//    @Test
//    public void mockStartServer_whenCommandIsWrong(){
//        String[] command = {"bash", "-c", "sudo /opt/tomcat/apache-tomcat-10.1.17/bin/stop.sh"};
//        State expectedState = State.RUNNING;
//        when(mocktomcatCommandService.changeTomcatState(command)).thenReturn(expectedState);
//        State actualState = macOSCommands.startServer();
//        assertNotEquals(expectedState, actualState);
//    }
//
//    @Test
//    public void mockStopServer(){
//        String[] command = {"bash", "-c", "sudo /opt/tomcat/apache-tomcat-10.1.17/bin/shutdown.sh"};
//        State expectedState = State.STOPPED;
//        when(mocktomcatCommandService.changeTomcatState(command)).thenReturn(expectedState);
//        State actualState = macOSCommands.stopServer();
//        assertEquals(expectedState, actualState);
//    }
//
//
//
//
//
//}