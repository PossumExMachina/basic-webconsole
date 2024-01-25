package monitoring.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import monitoring.common.State;
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DockerContainer {
    @JsonProperty("ID")
    private String ID;

    @JsonProperty("Image")
    private String Image;

    @JsonProperty("CreatedAt")
    private String CreatedAt;

    @JsonProperty("State")
    private String State;

    @JsonProperty("Names")
    private String name;

}

