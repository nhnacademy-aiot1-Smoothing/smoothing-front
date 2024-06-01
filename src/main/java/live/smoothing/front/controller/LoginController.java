package live.smoothing.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@CookieValue(value = "error", required = false) String error, Model model, HttpServletResponse response) {
        if (error != null) {
            model.addAttribute("error", URLDecoder.decode(error));
            Cookie cookie = new Cookie("error", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "pages/login";
    }

    @GetMapping("/login2")
    public String login2(@CookieValue(value = "error", required = false) String error, Model model, HttpServletResponse response) {
        if (error != null) {
            model.addAttribute("error", URLDecoder.decode(error));
            Cookie cookie = new Cookie("error", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "pages/login2";
    }
}