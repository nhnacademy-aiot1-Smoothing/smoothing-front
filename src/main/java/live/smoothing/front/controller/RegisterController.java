package live.smoothing.front.controller;

import live.smoothing.front.auth.dto.email.EmailCertificationRequest;
import live.smoothing.front.auth.dto.email.VerificationRequest;
import live.smoothing.front.auth.service.AuthService;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/register")
    public String register() {

        return "pages/register";
    }

    @ResponseBody
    @PostMapping("/register")
    public void createUser(@RequestBody UserCreateRequest request) {

        userService.createUser(request);
        log.info("회원 가입 완료, 아이디:{}", request.getUserId());
    }

    @ResponseBody
    @PostMapping("/requestCertificationNumber")
    public void certificationNumber(@RequestBody EmailCertificationRequest request) {

        authService.requestCertificationNumber(request);
    }

    @ResponseBody
    @PostMapping("/verifyCertificationNumber")
    public void verifyCertificationNumber(@RequestBody VerificationRequest request) {

        authService.verifyCertificationNumber(request);
    }
}
