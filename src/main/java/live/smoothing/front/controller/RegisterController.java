package live.smoothing.front.controller;

import live.smoothing.front.auth.service.AuthService;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final AuthService authService;
    @GetMapping("/register")
    public String register() {

        return "pages/register";
    }

    @PostMapping("/register")
    public String createUser(UserCreateRequest request) {

        log.info("아이디 : {}", request.getUserId());

        userService.createUser(request);

        log.info("회원 가입 완료");


        return "pages/login";
    }
}
