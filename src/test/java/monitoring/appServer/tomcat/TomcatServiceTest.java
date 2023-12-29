package monitoring.appServer.tomcat;

import monitoring.commands.CommandStrategy;
import monitoring.serverResources.memory.MemoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TomcatServiceTest {


    @Mock
    private CommandStrategy mockCommandStrategy;

    private TomcatService tomcatService;

    @BeforeEach
    void setUp() {

        mockCommandStrategy = Mockito.mock(CommandStrategy.class);
        tomcatService = new TomcatService();
        ReflectionTestUtils.setField(tomcatService, "commandStrategy", mockCommandStrategy);
    }


    @Test
    public void testStopTomcat_whenEverythingOK(){

        TomcatState expectedState = TomcatState.STOPPED;

       when(tomcatService.stopTomcat()).thenReturn(expectedState);

        TomcatState actualState = tomcatService.stopTomcat();
        assertEquals(expectedState, actualState);
    }

    @Test
    public void testStartTomcat_whenEverythingOK(){

        TomcatState expectedState = TomcatState.RUNNING;

        when(tomcatService.startTomcat()).thenReturn(expectedState);

        TomcatState actualState = tomcatService.startTomcat();
        assertEquals(expectedState, actualState);
    }

}