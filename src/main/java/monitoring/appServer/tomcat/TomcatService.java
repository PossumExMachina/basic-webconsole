package monitoring.appServer.tomcat;

import monitoring.commands.CommandStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TomcatService {

    @Autowired
    private CommandStrategy commandStrategy;


    public TomcatState stopTomcat() {
        return commandStrategy.stopServer();
    }

    public TomcatState startTomcat() {
        return commandStrategy.startServer();
    }




}



