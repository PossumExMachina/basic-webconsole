package monitoring.appServer.tomcat;

import monitoring.appServer.common.State;
import monitoring.commands.CommandStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TomcatService {

    @Autowired
    private CommandStrategy commandStrategy;


    public State stopTomcat() {
        return commandStrategy.stopServer();
    }

    public State startTomcat() {
        return commandStrategy.startServer();
    }




}



