
package monitoring.authentication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "error", defaultValue = "false") Boolean error) {
        logger.info("inside login controller");
        return new ModelAndView("login.html").addObject("error", error);
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        logger.info("logout");
        return "logout-success.html";
    }

}

