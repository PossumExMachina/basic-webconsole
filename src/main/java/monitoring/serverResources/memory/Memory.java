package monitoring.serverResources.memory;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Memory {

    private String total;
    private String used;
    private String free;

}
