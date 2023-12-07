package monitoring.application;

import monitoring.tomcat.TomcatStatus;
import net.minidev.json.parser.JSONParser;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationStatusTest {
//
//    private ApplicationService applicationService;
//    private ApplicationStatus applicationStatus;
//
//
//    @BeforeEach
//    public void setUp() {
//        applicationStatus = new ApplicationStatus();
//        applicationService = new ApplicationService(applicationStatus);
//        applicationService = spy(applicationService);
//    }
//
//
//    @Test
//    public void testDefaultConstructor() {
//        ApplicationStatus applicationStatus = new ApplicationStatus();
//        assertNotNull(applicationStatus, "ApplicationStatus instance should not be null");
//    }


//
//    @Test
//    public void whenHttpCallSucceeds_thenRunningAppsUpdated() throws Exception {
//
//        JSONObject mockResponse = new JSONObject();
//        mockResponse.has("id");
//
//
//        doReturn(mockResponse).when(applicationService).makeHttpRequest(anyString());
//
//        applicationService.getRunningAndNotRunningApps();
//
//        assertFalse(applicationStatus.getRunningApps().isEmpty(), "Running apps list should be updated");
//        assertTrue(applicationStatus.getNotRunningApps().isEmpty(), "Not running apps list should be empty on successful response");
//    }
//
//        @Test
//        public void whenHttpCallFails_thenNotRunningAppsUpdated() {
//
//            applicationService.getRunningAndNotRunningApps();
//            assertTrue(applicationStatus.getRunningApps().isEmpty(), "Running apps list should be empty on failure");
//            assertFalse(applicationStatus.getNotRunningApps().isEmpty(), "Not running apps list should be updated on failure");
//        }


    }

