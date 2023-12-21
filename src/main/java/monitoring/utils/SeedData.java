package monitoring.utils;

import monitoring.authentication.UserService;
import monitoring.authentication.User;
import monitoring.authentication.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {
        User adminUser = new User();

        adminUser.setUsername("admin");
        adminUser.setPassword("password");
        adminUser.setUserType(UserType.ADMIN);

        if (userService.findByUsername(adminUser.getUsername()).isEmpty()){
            userService.save(adminUser);
        }


    }
}
