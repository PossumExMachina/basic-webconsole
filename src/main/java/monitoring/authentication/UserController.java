/*

package monitoring.authentication;


import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.BadRequestException;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static monitoring.authentication.Role.ADMIN;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "error", defaultValue = "false") Boolean error) {
        logger.info("inside login controller");
        return new ModelAndView("login.html").addObject("error", error);
    }
//    @GetMapping("/identify-yourself")
//    public String identifyUser(@RequestParam String name,
//                               HttpSession session) {
//        session.setAttribute("userName", name);
//        return "redirect:/";
//    }
//
//    @Secured("ROLE_ADMIN")
//    @PostMapping("/")
//    public String postIndex(@RequestParam(name = "_name") String name,
//                            @RequestParam String email,
//                            @RequestParam String password,
//                            @RequestParam UserType userType) {
//
//        if (Strings.isBlank(name) || Strings.isBlank(email)) {
//            throw new BadRequestException("Name and email are mandatory!");
//        }
//        userService.save(new User("adam", "bleble", ADMIN));
//        return "redirect:/";
//    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        logger.info("logout");
        return "logout-success.html";
    }

}

*/
