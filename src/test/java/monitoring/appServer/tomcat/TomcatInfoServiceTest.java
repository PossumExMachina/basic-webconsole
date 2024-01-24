package monitoring.appServer.tomcat;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import monitoring.appServer.application.Application;
import monitoring.appServer.application.ApplicationService;
import monitoring.common.State;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

public class TomcatInfoServiceTest {

    @Mock
    private TomcatService mockTomcatService;
    @Mock
    private ApplicationService mockApplicationService;
    @InjectMocks
    private TomcatInfoService tomcatInfoService;

    @Before
    public void setUp() {
        mockTomcatService = mock(TomcatService.class);
        mockApplicationService = mock(ApplicationService.class);
        tomcatInfoService = new TomcatInfoService();
        ReflectionTestUtils.setField(tomcatInfoService, "tomcatService", mockTomcatService);
        ReflectionTestUtils.setField(tomcatInfoService, "applicationService", mockApplicationService);
    }

    @Test
    public void testGetTomcatStatus() {
        State expectedState = State.RUNNING;
        Application application1 = new Application();
        Application application2 = new Application();

        List<Application> expectedApplications = Arrays.asList(application1, application2);

        when(mockTomcatService.getTomcatState()).thenReturn(expectedState);
        when(mockApplicationService.getApplications()).thenReturn(expectedApplications);
        TomcatDTO result = tomcatInfoService.getTomcatStatus();

        assertEquals(expectedState, result.getTomcatState());
        assertEquals(expectedApplications, result.getApplications());
    }
}