package monitoring.appServer.tomcat;

import monitoring.appServer.common.State;
import monitoring.commands.CommandStrategy;
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

        State expectedState = State.STOPPED;

       when(tomcatService.stopTomcat()).thenReturn(expectedState);

        State actualState = tomcatService.stopTomcat();
        assertEquals(expectedState, actualState);
    }

    @Test
    public void testStartTomcat_whenEverythingOK(){

        State expectedState = State.RUNNING;

        when(tomcatService.startTomcat()).thenReturn(expectedState);

        State actualState = tomcatService.startTomcat();
        assertEquals(expectedState, actualState);
    }

}