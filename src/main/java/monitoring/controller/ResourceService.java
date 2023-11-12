package monitoring.controller;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {
    public ResourceData getServerResources() throws IOException, InterruptedException {
        // Logic to gather server resources
        boolean tomcatRunning = isTomcatRunning();

    }


    private boolean isTomcatRunning() throws IOException, InterruptedException {
        String[] command = {"/bin/sh", "-c", "ps -ef | grep '[o]rg.apache.catalina.startup.Bootstrap start'"};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            // If the result has any lines, Tomcat is running
            return reader.lines().anyMatch(line -> line.contains("org.apache.catalina.startup.Bootstrap"));
        } finally {
            process.waitFor();
        }
    }



    private double memoryUsage() throws IOException {
        String[] command = {"/bin/sh", "-c", "free -m | awk '/Mem:/ { print $3/$2 * 100.0 }'"};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())){
            return reader.
        }
    }



}
