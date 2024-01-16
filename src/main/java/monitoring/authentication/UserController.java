package monitoring.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/adduser")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "adduser";
    }


    @PostMapping("/adduser")
    public  String register_user(@ModelAttribute User user){
        userService.save(user);
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(@ModelAttribute User user){
        return "login";
    }

}
