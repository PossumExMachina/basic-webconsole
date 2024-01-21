package monitoring.commands;

import monitoring.commands.control.CommandStrategy;
import monitoring.commands.control.LinuxCommands;
import monitoring.commands.control.macOSCommands;
import org.junit.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CommandStrategyConfigTest {

    @Test
    public void testCommandStrategyForMacOS(){
        setSystemProperty("Mac OS X");

        CommandStrategyConfig config = new CommandStrategyConfig();
        CommandStrategy strategy = config.commandStrategy();

        assertTrue(strategy instanceof macOSCommands);
    }

    @Test
    public void testCommandStrategyForLinux(){
        setSystemProperty("Linux");

        CommandStrategyConfig config = new CommandStrategyConfig();
        CommandStrategy strategy = config.commandStrategy();

        assertTrue(strategy instanceof LinuxCommands);
    }

    @Test
    public void testCommandStrategyForUnsupportedOS() {
        setSystemProperty("Windows");

        CommandStrategyConfig config = new CommandStrategyConfig();
        assertThrows(UnsupportedOperationException.class, config::commandStrategy);
    }

    private void setSystemProperty(String value) {
        System.setProperty("os.name", value);
    }
}