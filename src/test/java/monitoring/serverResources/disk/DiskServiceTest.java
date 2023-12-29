package monitoring.serverResources.disk;

import monitoring.commands.CommandExec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class DiskServiceTest {

    @Mock
    private CommandExec mockCommandExec;

    @InjectMocks
    private DiskService diskService;

    @BeforeEach
    void setUp() {

        mockCommandExec = Mockito.mock(CommandExec.class);
        diskService = new DiskService();
        ReflectionTestUtils.setField(diskService, "commandExec", mockCommandExec);
    }


    @Test
    public void testGetDiskUsage() throws IOException {
        List<String> mockOutput = Arrays.asList("Sample disk usage output", "Sample values");
        when(mockCommandExec.executeCommand(anyString())).thenReturn(mockOutput);

        List<String> result = diskService.getDiskUsage();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Sample disk usage output", result.get(0));
    }

    @Test
    public void testGetDiskUsageThrowsExceptionWhenEmptyOutput() throws IOException {
        when(mockCommandExec.executeCommand("df -h")).thenReturn(Collections.emptyList());

        assertThrows(IOException.class, () -> diskService.getDiskUsage());
    }




}