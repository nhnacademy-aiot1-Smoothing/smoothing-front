package live.smoothing.front.controller;

import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URLDecoder;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

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

    @ResponseBody
    @DeleteMapping("/oauth")
    public void deleteOAuth(@PathParam("providerName") String providerName, HttpServletResponse response) throws IOException {
        userService.deleteUserOAuth(providerName);
    }
}