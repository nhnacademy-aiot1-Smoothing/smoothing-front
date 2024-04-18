package live.smoothing.front.controller;

import live.smoothing.front.adapter.AuthAdapter;
import live.smoothing.front.email.dto.EmailCertificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private AuthAdapter authAdapter;

    @PostMapping("/email")
    public String requestCertificationNumber(EmailCertificationRequest request) {

        authAdapter.requestCertificationNumber(request);

        return "redirect:/register";
    }

}
