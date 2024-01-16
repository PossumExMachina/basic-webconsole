package monitoring.appServer.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TomcatWebappScannerServiceImpl implements TomcatWebappScannerService {
    private static final Logger logger = LoggerFactory.getLogger(TomcatWebappScannerServiceImpl.class);

    /**
      scans the directory where applications are deployed on tomcat and returns list of the applications names
      only returns applications that have META-INF dictionary inside their deployment dir
      */
    public Optional<List<String>> scanForWebapps(){

        File folder = new File(System.getenv("CATALINA_HOME") + "/webapps");
        logger.info(folder.getPath());
        String mandatoryFile = "META-INF"; // mandatory file is used to check if the directory is app directory
        File[] directories = folder.listFiles();
        List<String> appList = new ArrayList<>();

        if (directories != null) {
            for (File dir : directories) {
                File[] subdirs = dir.listFiles();
                if (subdirs != null) {                // checks if the mandatory file is present in subdirectory
                    for (File file : subdirs) {
                        if (file.getName().contains(mandatoryFile)) {
                            appList.add(dir.getName());
                        }
                    }
                }
            }
        }
        return Optional.of(appList);
    }

}
