package live.smoothing.front.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {

        return "pages/login";
    }

    @GetMapping("/register")
    public String register() {

        return "pages/register";
    }
}
