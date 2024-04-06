package monitoring.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import monitoring.common.State;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DockerContainer that)) return false;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(Image, that.Image) &&
                Objects.equals(name, that.name) &&
                Objects.equals(CreatedAt, that.CreatedAt) &&
                Objects.equals(State, that.State);

    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Image, State, CreatedAt, name);
    }

}

