package monitoring.appServer.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import monitoring.common.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ApplicationServiceTest {


    @Mock
    private URLService mockUrlService;

    @Mock
    private TomcatWebappScannerService mockTomcatWebappScannerService;
    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        applicationService = new ApplicationService();
        mockUrlService = Mockito.mock(URLService.class);
        mockTomcatWebappScannerService = Mockito.mock(TomcatWebappScannerService.class);
        ReflectionTestUtils.setField(applicationService, "urlService", mockUrlService);
        ReflectionTestUtils.setField(applicationService, "tomcatWebappScannerService", mockTomcatWebappScannerService);
    }

    @Test
    void getApplicationState_WhenAppNotRunning() throws IOException {
        String appName = "TestApp";
        String mockUrl = "http://example.com";
        JsonNode mockJson = Mockito.mock(JsonNode.class);
        JsonNode mockStatusNode = Mockito.mock(JsonNode.class);

        when(mockStatusNode.toString()).thenReturn("Down");
        when(mockJson.get("status")).thenReturn(mockStatusNode);
        when(mockUrlService.createURL(appName)).thenReturn(mockUrl);
        when(mockUrlService.makeHttpRequest(mockUrl)).thenReturn(mockJson);

        State state = applicationService.getApplicationStatus(appName);

        assertEquals(State.STOPPED, state, "Expected the application status to be STOPPED.");
    }

    @Test
    void getApplicationsState_WhenAppRunning() throws IOException {
        String appName = "TestApp";
        String mockUrl = "http://example.com";
        JsonNode mockJson = Mockito.mock(JsonNode.class);
        JsonNode mockStatusNode = Mockito.mock(JsonNode.class);

        when(mockStatusNode.toString()).thenReturn("UP");
        when(mockJson.get("status")).thenReturn(mockStatusNode);
        when(mockUrlService.createURL(appName)).thenReturn(mockUrl);
        when(mockUrlService.makeHttpRequest(mockUrl)).thenReturn(mockJson);

        State state = applicationService.getApplicationStatus(appName);

        assertEquals(State.RUNNING, state, "Expected the application status to be RUNNING.");
    }

    @Test
    void getApplicationsState_WhenIOException() throws IOException {
        String appName = "TestApp";
        String mockUrl = "http://example.com";

        when(mockUrlService.createURL(appName)).thenReturn(mockUrl);
        when(mockUrlService.makeHttpRequest(mockUrl)).thenThrow(new IOException());

        State state = applicationService.getApplicationStatus(appName);

        assertEquals(State.STOPPED, state, "Expected the application status to be STOPPED when IOException occurs.");
    }

    @Test
    void getApplications_WhenNoWebappsFound_ShouldReturnEmptyList() {

        when(mockTomcatWebappScannerService.scanForWebapps()).thenReturn(Optional.empty());

        List<Application> result = applicationService.getApplications();

        assertEquals(0, result.size(), "Expected an empty list of applications.");
    }



    @Disabled
    @Test
    void getApplications_WhenWebappsFound_ShouldReturnListOfApplications() throws IOException {
        List<String> mockAppNames = Arrays.asList("app1", "app2");

        when(mockTomcatWebappScannerService.scanForWebapps()).thenReturn(Optional.of(mockAppNames));

        String mockUrl = "http://example.com";
        JsonNode mockJsonUp = Mockito.mock(JsonNode.class);
        when(mockJsonUp.get("status").toString()).thenReturn("UP");
        when(mockUrlService.createURL(anyString())).thenReturn(mockUrl);
        when(mockUrlService.makeHttpRequest(mockUrl)).thenReturn(mockJsonUp);

        ApplicationService spyApplicationService = Mockito.spy(applicationService);
        when(spyApplicationService.getApplicationStatus("app1")).thenReturn(State.RUNNING);
        when(spyApplicationService.getApplicationStatus("app2")).thenReturn(State.STOPPED);

        List<Application> result = spyApplicationService.getApplications();

        assertEquals(2, result.size(), "Expected two applications in the result list.");
        assertEquals(State.RUNNING, result.get(0).getState(), "Expected app1 to be RUNNING.");
        assertEquals(State.STOPPED, result.get(1).getState(), "Expected app2 to be STOPPED.");
    }


}