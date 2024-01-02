package monitoring.serverResources.disk;

import lombok.*;

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

}
