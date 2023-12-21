package monitoring.commands;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandStrategyConfig {

    @Bean
    public CommandStrategy commandStrategy(){
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")){
            return new MacOSCommands();
        }
        else if(osName.contains("linux")){
            return new LinuxCommands();
        }
        else {
            throw new UnsupportedOperationException("Unsupported operating system");
        }
    }
}
