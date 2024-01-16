package monitoring.appServer.common;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ResourceContext {

    private String resourceId;

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

}
