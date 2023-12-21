package monitoring.appServer.tomcat;

import monitoring.commands.CommandStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class TomcatService {

    @Autowired
    private CommandStrategy commandStrategy;

    public TomcatService() {

    }

    public TomcatState stopTomcat() {
        return commandStrategy.stopServer();
    }

    public TomcatState startTomcat() {
        return commandStrategy.startServer();
    }




}



