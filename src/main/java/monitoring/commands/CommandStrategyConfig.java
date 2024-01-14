package monitoring.commands;

import monitoring.commands.control.CommandStrategy;
import monitoring.commands.control.LinuxCommands;
import monitoring.commands.control.macOSCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandStrategyConfig {


    /**
     * Provides a CommandStrategy bean based on the operating system.
     *
     * Determines the operating system of the current environment and returns
     * an appropriate CommandStrategy implementation. Supports macOS and Linux.
     *
     * @return CommandStrategy An instance of the appropriate CommandStrategy implementation.
     * @throws UnsupportedOperationException If the operating system is not supported.
     */
    @Bean
    public CommandStrategy commandStrategy(){
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")){
            return new macOSCommands();
        }
        else if(osName.contains("linux")){
            return new LinuxCommands();
        }
        else {
            throw new UnsupportedOperationException("Unsupported operating system");
        }
    }
}
