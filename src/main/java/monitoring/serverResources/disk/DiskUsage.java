package monitoring.serverResources.disk;

import lombok.*;
import monitoring.docker.DockerContainer;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DiskUsage {
    private String fileSystem;
    private String fileSystemSize;
    private String used;
    private String available;
    private int capacity;
    private String iused;
    private String ifree;
    private String mountedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiskUsage that)) return false;
        return Objects.equals(fileSystem, that.fileSystem) &&
                Objects.equals(fileSystemSize, that.fileSystemSize) &&
                Objects.equals(used, that.used) &&
                Objects.equals(available, that.available) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(iused, that.iused) &&
                Objects.equals(ifree, that.ifree) &&
                Objects.equals(mountedOn, that.mountedOn);

    }

    @Override
    public int hashCode() {
        return Objects.hash(fileSystem, fileSystemSize, used, available, capacity, iused, ifree, mountedOn);
    }


}
