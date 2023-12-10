package monitoring.serverResources.disk;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiskService {

    public DiskService() {
    }

    public List<String> getDiskUsage() throws IOException {
        List<String> diskUsage = new ArrayList<>();
        Process p = null;
        try {
            p = new ProcessBuilder().command("bash","-c","df -h").start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String s;
        while((s = br.readLine()) != null)
        {
            diskUsage.add(s + "\n");
        }
        return diskUsage;
    }

}
