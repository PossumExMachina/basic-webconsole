//package monitoring.commands;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//class CommandStrategyConfigTest {
//
//    private CommandStrategyConfig commandStrategyConfig;
//    private AbstractApplicationContext context;
//
//    @Mock
//    CommandExec mockCommandExec;
//
//    @BeforeEach
//    void setUp() {
//        context = new AnnotationConfigApplicationContext(CommandStrategyConfig.class);
//        mockCommandExec = Mockito.mock(CommandExec.class);
//        ReflectionTestUtils.setField(commandStrategyConfig, "commandExec", mockCommandExec);
//    }
//
//    @AfterEach
//    void tearDown() {
//        context.close();
//    }
//
//
//    @Test
//    void testMacOSCommandStrategy() {
//        System.setProperty("os.name", "Mac OS X");
//        context.refresh();
//        assertTrue(context.getBean(CommandStrategy.class) instanceof MacOSCommands);
//    }
//
//    @Test
//    void testLinuxCommandStrategy() {
//        System.setProperty("os.name", "Linux");
//        context.refresh();
//        assertTrue(context.getBean(CommandStrategy.class) instanceof LinuxCommands);
//    }
//
//    @Test
//    void testUnsupportedOS() {
//        System.setProperty("os.name", "Windows");
//        context.refresh();
//        assertThrows(UnsupportedOperationException.class, () -> context.getBean(CommandStrategy.class));
//    }
//
//}