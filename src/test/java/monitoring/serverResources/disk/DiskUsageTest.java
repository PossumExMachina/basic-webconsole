package monitoring.serverResources.disk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiskUsageTest {
    @Test
    public void testGetDiskUsage(){
        DiskUsage diskUsage = new DiskUsage();
        diskUsage.setAvailable("10");
        diskUsage.setCapacity(2);
        diskUsage.setFileSystem("/dev");
        diskUsage.setFileSystemSize("10");
        diskUsage.setIfree("10");
        diskUsage.setIused("0");
        diskUsage.setMountedOn("/test");
        diskUsage.setUsed("0");


        assertAll(
                () -> assertEquals("10", diskUsage.getAvailable()),
                () -> assertEquals(2, diskUsage.getCapacity()),
                () -> assertEquals("/dev", diskUsage.getFileSystem()),
                () -> assertEquals("10", diskUsage.getFileSystemSize()),
                () -> assertEquals("10", diskUsage.getIfree()),
                () -> assertEquals("0", diskUsage.getIused()),
                () -> assertEquals("/test", diskUsage.getMountedOn()),
                () -> assertEquals("0", diskUsage.getUsed()));
    }


    @Test
    void toString_ShouldReturnCorrectFormat() {
        DiskUsage diskUsage = new DiskUsage(
                "/dev/sda1", "500GB", "200GB", "300GB", 40, "100000", "200000", "/");

        String diskUsageString = diskUsage.toString();

        String expectedString = "DiskUsage(fileSystem=/dev/sda1, fileSystemSize=500GB, used=200GB, " +
                "available=300GB, capacity=40, iused=100000, ifree=200000, " +
                "mountedOn=/)";
        assertEquals(expectedString, diskUsageString, "The toString method should return the correct string representation of DiskUsage object.");
    }
}