package monitoring.utils;

import monitoring.appServer.tomcat.TomcatCommandService;
import monitoring.authentication.UserService;
import monitoring.authentication.User;
import monitoring.authentication.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(TomcatCommandService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        User adminUser = new User();

       logger.info("Running commandLineRunner");
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("password"));
        adminUser.setRole(Role.ADMIN);

        if (userService.findByUsername(adminUser.getUsername()).isEmpty()){
            userService.save(adminUser);
            logger.info("Creating admin user");

           }

        System.out.println("admin user created: " + userService.findByUsername("admin").isPresent());



    }
}
