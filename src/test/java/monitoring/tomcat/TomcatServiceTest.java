package monitoring.tomcat;

import monitoring.ProcessBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TomcatServiceTest {

    @Mock
    private ProcessBuilderFactory processBuilderFactory;
    @Mock
    private ProcessBuilder processBuilder;
    @Mock
    private Process process;

    private TomcatService tomcatService;


    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(processBuilderFactory.createProcessBuilder(any(String[].class))).thenReturn(processBuilder);
        when(processBuilder.start()).thenReturn(process);
        tomcatService = new TomcatService(processBuilderFactory);
    }

    @Test
    public void testIsTomcatRunning() throws IOException {

        String simulatedProcessOutput = "root      1234     1  0 Nov10 ?        00:00:00 /opt/tomcat/bin/org.apache.catalina.startup.Bootstrap start";
        ByteArrayInputStream bais = new ByteArrayInputStream(simulatedProcessOutput.getBytes());
        Process mockProcess = mock(Process.class);

        when(processBuilderFactory.createProcessBuilder(any(String[].class))).thenReturn(processBuilder);
        when(processBuilder.start()).thenReturn(mockProcess);
        when(mockProcess.getInputStream()).thenReturn(bais);

        boolean result = tomcatService.isTomcatRunning();

        assertTrue(result);
    }

    @Test
    public void testIsTomcatNotRunning() throws IOException {

        String simulatedProcessOutput = "tomcat is not running";
        ByteArrayInputStream bais = new ByteArrayInputStream(simulatedProcessOutput.getBytes());
        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);


        when(processBuilderFactory.createProcessBuilder(any(String[].class))).thenReturn(mockProcessBuilder);
        when(mockProcessBuilder.start()).thenReturn(process);
        when(process.getInputStream()).thenReturn(bais);

        boolean result = tomcatService.isTomcatRunning();

        assertFalse(result);
    }



    @Test
    public void testIsTomcatRunningIOException() throws IOException {

        when(processBuilderFactory.createProcessBuilder(any(String[].class))).thenReturn(processBuilder);
        when(processBuilder.start()).thenThrow(new IOException("Test exception"));

        boolean result = tomcatService.isTomcatRunning();

        assertFalse(result);

    }



}