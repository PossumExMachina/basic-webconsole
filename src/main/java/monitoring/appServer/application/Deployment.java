package monitoring.appServer.application;

import monitoring.commands.CommandExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Deployment {

    @Autowired
    CommandExec commandExec;


    public void copyWarIntoWebapps(File file){
        try {
            commandExec.executeCommand("cp file /opt/prod/webapps");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void findWarToDeploy(){

    }

}
