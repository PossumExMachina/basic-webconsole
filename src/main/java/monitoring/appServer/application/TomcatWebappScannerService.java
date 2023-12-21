package monitoring.appServer.application;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TomcatWebappScannerService {

    public Optional<List<String>> scanForWebapps(){
       /*
       scans the directory where applications are deployed on tomcat and returns list of the applications names
       only returns applications that have META-INF dictionary inside their deployment dir
       */

        File folder = new File("/opt/tomcat/apache-tomcat-10.1.17/webapps");  //TODO: change it so it is not hardcoded

        String mandatoryFile = "META-INF"; // mandatory file is used to check if the directory is app directory
        File[] directories = folder.listFiles();
        List<String> nameList = new ArrayList<>();

        if (directories != null) {
            for (File dir : directories) {
                File[] subdirs = dir.listFiles();
                if (subdirs != null) {                // checks if the mandatory file is present in subdirectory
                    for (File file : subdirs) {
                        if (file.getName().contains(mandatoryFile)) {
                            nameList.add(dir.getName());
                        }
                    }
                }
            }
        }
        return Optional.of(nameList);
    }

}
