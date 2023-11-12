package monitoring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MonitoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitoringApplication.class, args);
    }

        @Bean
        public CommandLineRunner run() {
            return args -> {
                System.out.println("The application has started. Replace this message with server info display logic.");
            };
        }

    private void fetchServerInformation() {
        System.out.println("Fetching server information...");
        // TODO: Implement actual server info fetching logic here
    }




    }

