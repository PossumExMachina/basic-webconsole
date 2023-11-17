package monitoring.controller;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ResourceService {
    public ResourceData getServerResources() throws IOException, InterruptedException {
        // Logic to gather server resources
        boolean tomcatRunning = isTomcatRunning();
       // boolean appRunningOnTomcat = isAppRunningOnTomcat();

        return new ResourceData(tomcatRunning);

    }


    private boolean isTomcatRunning() throws IOException, InterruptedException {
        String[] command = {"/bin/sh", "-c", "ps -ef | grep '[o]rg.apache.catalina.startup.Bootstrap start'"};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.lines().anyMatch(line -> line.contains("org.apache.catalina.startup.Bootstrap"));
        } finally {
            process.waitFor();
        }
    }

//    private boolean isAppRunningOnTomcat() throws IOException, InterruptedException{
//        Path dir = Paths.get("/var/lib/tomcat9/webapps");
//        try (Stream<Path> stream = Files.list(dir);){
//
//        }
//
//
//
//    }



//    private double memoryUsage() throws IOException {
//        String[] command = {"/bin/sh", "-c", "free -m | awk '/Mem:/ { print $3/$2 * 100.0 }'"};
//        ProcessBuilder processBuilder = new ProcessBuilder(command);
//        Process process = processBuilder.start();
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())){
//            return reader.
//        }
//    }



}

