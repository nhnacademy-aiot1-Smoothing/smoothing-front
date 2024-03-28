package live.smoothing.front.user.controller;

import live.smoothing.front.user.dto.UserCreateRequest;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String createUser(@ModelAttribute UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest);
        return "redirect:/";
    }
}