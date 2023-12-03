package monitoring;

import org.springframework.stereotype.Component;

@Component
public class ProcessBuilderFactory {
    public ProcessBuilder createProcessBuilder(String... command) {
        return new ProcessBuilder(command);
    }
}
