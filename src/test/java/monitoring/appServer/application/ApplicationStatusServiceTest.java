package monitoring.appServer.application;
import com.fasterxml.jackson.databind.JsonNode;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;



class ApplicationStatusServiceTest {


    @Mock
    private URLService mockUrlService;
    @InjectMocks
    private ApplicationStatusService applicationStatusService;

    @BeforeEach
    void setUp() {
        applicationStatusService = new ApplicationStatusService();
        mockUrlService = Mockito.mock(URLService.class);
        ReflectionTestUtils.setField(applicationStatusService, "urlService", mockUrlService);
    }

    @Disabled
    @Test
    void testGetNotRunningApps_WhenAllAppsRunning() throws Exception {
        // Mock the URLService's behavior
        Mockito.when(mockUrlService.getURL()).thenReturn(List.of("http://app1", "http://app2"));
        JsonNode mockJson = Mockito.mock(JsonNode.class);
        Mockito.when(mockJson.path("status").asText()).thenReturn("UP");
        Mockito.when(mockUrlService.makeHttpRequest(anyString())).thenReturn(mockJson);

        List<String> notRunningApps = applicationStatusService.getNotRunningApps();

        assertTrue(notRunningApps.isEmpty());
    }
    @Disabled
    @Test
    void testGetNotRunningApps_WhenSomeAppsAreRunning() throws IOException, ParseException {
        // Setup URLs
        Mockito.when(mockUrlService.getURL()).thenReturn(List.of("http://app1", "http://app2", "http://app3"));

        // Create JSONObjects for different statuses
        JsonNode mockJsonUp = Mockito.mock(JsonNode.class);
        Mockito.when(mockJsonUp.path("status").asText()).thenReturn("UP");
        JsonNode mockJsonDown = Mockito.mock(JsonNode.class);
        Mockito.when(mockJsonUp.path("status").asText()).thenReturn("Down");

        // Setup mock behavior for specific URLs
        Mockito.when(mockUrlService.makeHttpRequest("http://app1")).thenReturn(mockJsonUp); // app1 is UP
        Mockito.when(mockUrlService.makeHttpRequest("http://app2")).thenReturn(mockJsonDown); // app2 is DOWN
        Mockito.when(mockUrlService.makeHttpRequest("http://app3")).thenReturn(mockJsonDown); // app3 is DOWN

        // Call the methods under test
        List<String> notRunningApps = applicationStatusService.getNotRunningApps();
        List<String> runningApps = applicationStatusService.getRunningApps();

        // Assertions
        assertEquals(2, notRunningApps.size()); // Expect 2 apps not running (app2 and app3)
        assertEquals(1, runningApps.size()); // Expect 1 app running (app1)
    }

    @Disabled
    @Test
    void testGetNotRunningApps_WhenAllAppsAreRunning() throws IOException, ParseException {
        Mockito.when(mockUrlService.getURL()).thenReturn(List.of("http://app1", "http://app2", "http://app3"));

        JsonNode mockJsonUp = Mockito.mock(JsonNode.class);
        Mockito.when(mockJsonUp.path("status").asText()).thenReturn("UP");


        Mockito.when(mockUrlService.makeHttpRequest(anyString())).thenReturn(mockJsonUp);

        List<String> notRunningApps = applicationStatusService.getNotRunningApps();
        List<String> runningApps = applicationStatusService.getRunningApps();

        assertEquals(0, notRunningApps.size());
        assertEquals(3, runningApps.size());

    }









}