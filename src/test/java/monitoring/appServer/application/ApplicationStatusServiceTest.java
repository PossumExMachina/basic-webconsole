package monitoring.appServer.application;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Disabled // Until i resolve Jackson issues
class ApplicationStatusServiceTest {


    @Mock
    private URLService mockUrlService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private ApplicationStatusService applicationStatusService;

    @BeforeEach
    void setUp() {
        applicationStatusService = new ApplicationStatusService();
        mockUrlService = Mockito.mock(URLService.class);
        ReflectionTestUtils.setField(applicationStatusService, "urlService", mockUrlService);
    }


    @Disabled // Until i resolve Jackson issues
    @Test
    void testGetNotRunningApps_WhenAllAppsRunning() throws Exception {
        when(mockUrlService.getURL()).thenReturn(Arrays.asList("http://app1", "http://app2"));
        JsonNode upNode = objectMapper.createObjectNode().put("status", "down");
        upNode.toString();
        when(mockUrlService.makeHttpRequest(anyString())).thenReturn(upNode);

        List<String> notRunningApps = applicationStatusService.getNotRunningApps();

        assertTrue(notRunningApps.isEmpty());
    }

    @Disabled // Until i resolve Jackson issues
    @Test
    void testGetNotRunningApps_WhenSomeAppsAreRunning() throws IOException, ParseException {
        // Setup URLs
        when(mockUrlService.getURL()).thenReturn(List.of("http://app1", "http://app2", "http://app3"));

        // Create JSONObjects for different statuses
        JsonNode mockJsonUp = Mockito.mock(JsonNode.class);
        when(mockJsonUp.path("status").asText()).thenReturn("UP");
        JsonNode mockJsonDown = Mockito.mock(JsonNode.class);
        when(mockJsonUp.path("status").asText()).thenReturn("Down");

        // Setup mock behavior for specific URLs
        when(mockUrlService.makeHttpRequest("http://app1")).thenReturn(mockJsonUp); // app1 is UP
        when(mockUrlService.makeHttpRequest("http://app2")).thenReturn(mockJsonDown); // app2 is DOWN
        when(mockUrlService.makeHttpRequest("http://app3")).thenReturn(mockJsonDown); // app3 is DOWN

        // Call the methods under test
        List<String> notRunningApps = applicationStatusService.getNotRunningApps();
        List<String> runningApps = applicationStatusService.getRunningApps();

        // Assertions
        assertEquals(2, notRunningApps.size()); // Expect 2 apps not running (app2 and app3)
        assertEquals(1, runningApps.size()); // Expect 1 app running (app1)
    }


    @Disabled // Until i resolve Jackson issues
    @Test
    void testGetNotRunningApps_WhenAllAppsAreRunning() throws IOException, ParseException {
        when(mockUrlService.getURL()).thenReturn(List.of("http://app1", "http://app2", "http://app3"));

        JsonNode mockJsonUp = Mockito.mock(JsonNode.class);
        when(mockJsonUp.path("status").asText()).thenReturn("UP");


        when(mockUrlService.makeHttpRequest(anyString())).thenReturn(mockJsonUp);

        List<String> notRunningApps = applicationStatusService.getNotRunningApps();
        List<String> runningApps = applicationStatusService.getRunningApps();

        assertEquals(0, notRunningApps.size());
        assertEquals(3, runningApps.size());

    }









}