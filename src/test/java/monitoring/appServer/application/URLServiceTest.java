package monitoring.appServer.application;

import com.fasterxml.jackson.databind.JsonNode;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class URLServiceTest {



    @Mock
    private URLService urlService;

    @Mock
    TomcatWebappScannerService tomcatWebappScannerService;

    @Test
    void testGetURL(){
        when(tomcatWebappScannerService.scanForWebapps()).thenReturn(Optional.of(Arrays.asList("app1", "app2")));

        List<String> actualUrlList = urlService.getURL();



        // Optionally, assert the contents of the list
        assertEquals(1, actualUrlList.size());
        assertTrue(actualUrlList.contains("http://localhost:8080/app1/actuator/health"));

        // Assert that the actualUrlList is not empty
        assertFalse(actualUrlList.isEmpty());

    }

    @Test
    void testMakeHttpRequest() throws IOException, ParseException {
        URL testUrl = new URL("http://localhost:8080/nsme/actuator/health");
        JsonNode expectedResponse = Mockito.mock(JsonNode.class);
        JsonNode statusNode = Mockito.mock(JsonNode.class);

        Mockito.when(statusNode.asText()).thenReturn("UP");
        Mockito.when(expectedResponse.path("status")).thenReturn(statusNode);

        Mockito.when(urlService.makeHttpRequest(testUrl.toString())).thenReturn(expectedResponse);

        JsonNode actualResponse = urlService.makeHttpRequest(testUrl.toString());

        assertNotNull(actualResponse);
        assertEquals("UP", actualResponse.path("status").asText());
    }

    @Test
    void testHttpRequest_WhenURLMalformed() throws IOException {
        String malformedUrl = "kkj:localhost:8080/nsme/actuator/health";
        JSONObject expectedResponse = new JSONObject();
        expectedResponse.put("status", "UP");


        when(urlService.makeHttpRequest(malformedUrl)).thenThrow(new MalformedURLException("Malformed URL"));

        assertThrows(MalformedURLException.class, () -> {
            urlService.makeHttpRequest(malformedUrl);
        });
    }


}