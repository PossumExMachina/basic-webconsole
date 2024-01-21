package monitoring.serverResources.memory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryTest {

    @Test
    public void testGetMemory(){
        Memory memory = new Memory();
        memory.setFree("0");
        memory.setUsed("0");
        memory.setTotal("0");


        assertAll(
                () -> assertEquals("0", memory.getFree()),
                () -> assertEquals("0", memory.getUsed()),
                () -> assertEquals("0", memory.getTotal()));
    }


    @Test
    void toString_ShouldReturnCorrectFormat() {

        Memory memory = new Memory("16GB", "8GB", "8GB");
        String memoryString = memory.toString();

        String expectedString = "Memory(total=16GB, used=8GB, free=8GB)";
        assertEquals(expectedString, memoryString, "The toString method should return the correct string representation of Memory object.");
    }

}