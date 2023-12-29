//package monitoring.commands;
//
//import monitoring.appServer.tomcat.TomcatCommandService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//public abstract class AbstractCommandStrategy implements CommandStrategy {
//
//    @Autowired
//    protected CommandExec commandExec;
//
//    @Autowired
//    protected TomcatCommandService tomcatCommandService;
//
//    protected abstract String getMemoryCommand();
//    @Override
//    public List<String> getFreeMemory() {
//        String command = getMemoryCommand();
//        try {
//            return executeCommandWithExceptionHandling(command);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    private List<String> executeCommandWithExceptionHandling(String command) throws IOException {
//        try {
//            return commandExec.executeCommand(command);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}

