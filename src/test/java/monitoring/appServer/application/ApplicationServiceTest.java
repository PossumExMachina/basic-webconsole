package monitoring.appServer.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import monitoring.common.State;
import org.junit.jupiter.api.BeforeEach;
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
    private final ObjectMapper objectMapper = new ObjectMapper();
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

//    @Test
//    void getApplications_WhenWebappsFound_ShouldReturnListOfApplications() throws IOException {
//        when(mockTomcatWebappScannerService.scanForWebapps()).thenReturn(Optional.of(Arrays.asList("app1", "app2")));
//        when(applicationService.getApplicationStatus(anyString())).thenReturn(State.RUNNING);
//
//        List<Application> result = applicationService.getApplications();
//
//        assertEquals(2, result.size(), "Expected two applications in the result list.");
//        assertEquals(State.RUNNING, result.get(0).getState(), "Expected the application state to be RUNNING.");
//    }


    @Test
    void getApplications_WhenNoWebappsFound_ShouldReturnEmptyList() {

        when(mockTomcatWebappScannerService.scanForWebapps()).thenReturn(Optional.empty());

        List<Application> result = applicationService.getApplications();

        assertEquals(0, result.size(), "Expected an empty list of applications.");
    }


}