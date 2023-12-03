package monitoring.application;

import monitoring.ProcessBuilderFactory;
import monitoring.tomcat.TomcatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class URLServiceTest {

    private ApplicationService applicationService;
    private URLService urlService;
    @BeforeEach
    void setUp() throws IOException {
        applicationService = mock(ApplicationService.class);
        urlService = new URLService();
        urlService.applicationService = applicationService;

    }

    @Test
    void testGetURL() {
        when(applicationService.getApplicationName()).thenReturn(Arrays.asList("app1", "app2"));

        List<String> urls = urlService.getURL();

        assertEquals(2, urls.size());
        assertTrue(urls.contains("http://localhost:8080/app1/actuator/health"));
        assertTrue(urls.contains("http://localhost:8080/app2/actuator/health"));
    }







}