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

        String simulatedProcessOutput = "tomcat.service - Apache Tomcat Web Application Container\n" +
                "     Loaded: loaded (/etc/systemd/system/tomcat.service; disabled; vendor preset: enabled)\n" +
                "     Active: active (running) since Thu 2023-12-07 19:26:15 UTC; 1s ago\n" +
                "    Process: 3116 ExecStart=/opt/tomcat/bin/startup.sh (code=exited, status=0/SUCCESS)\n" +
                "   Main PID: 3126 (java)\n" +
                "      Tasks: 21 (limit: 9336)\n" +
                "     Memory: 322.3M\n" +
                "        CPU: 2.956s\n" +
                "     CGroup: /system.slice/tomcat.service\n" +
                "             \\u2514\\u25003126 /usr/lib/jvm/java-1.19.0-openjdk-arm64/bin/java -Djava.util.logging.config.file=/opt/tomcat/conf/logging.properties -Djava.util.logging.m>\n" +
                "\n" +
                "Dec 07 19:26:15 raccoonbuntu systemd[1]: Starting Apache Tomcat Web Application Container...\n" +
                "Dec 07 19:26:15 raccoonbuntu startup.sh[3116]: Existing PID file found during start.\n" +
                "Dec 07 19:26:15 raccoonbuntu startup.sh[3116]: Removing/clearing stale PID file.\n" +
                "Dec 07 19:26:15 raccoonbuntu startup.sh[3116]: Tomcat started.\n" +
                "Dec 07 19:26:15 raccoonbuntu systemd[1]: Started Apache Tomcat Web Application Container.";
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

        String simulatedProcessOutput = "Apache Tomcat Web Application Container\n" +
                "     Loaded: loaded (/etc/systemd/system/tomcat.service; disabled; vendor preset: enabled)\n" +
                "     Active: inactive (dead)";
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