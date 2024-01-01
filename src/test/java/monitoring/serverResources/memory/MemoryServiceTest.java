package monitoring.serverResources.memory;

import monitoring.commands.CommandExec;
import monitoring.commands.CommandStrategy;

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

class MemoryServiceTest {

    @Mock
    private CommandStrategy mockCommandStrategy;

    @InjectMocks
    private MemoryService memoryService;

    @BeforeEach
    void setUp() {

        mockCommandStrategy = Mockito.mock(CommandStrategy.class);
        memoryService = new MemoryService();
        ReflectionTestUtils.setField(memoryService, "commandStrategy", mockCommandStrategy);
    }


    @Test
    public void testGetMemory() {
        List<String> mockOutput = Arrays.asList("Sample free memory output", "Sample values");
        when(memoryService.getFreeMemory()).thenReturn(mockOutput);

        List<String> result = memoryService.getFreeMemory();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Sample free memory output", result.get(0));
    }



}