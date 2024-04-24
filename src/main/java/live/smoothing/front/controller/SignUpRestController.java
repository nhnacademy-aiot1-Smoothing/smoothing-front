package live.smoothing.front.controller;

import live.smoothing.front.adapter.AuthAdapter;
import live.smoothing.front.email.dto.CertificationNumberResponse;
import live.smoothing.front.email.dto.EmailCertificationRequest;
import live.smoothing.front.user.adapter.UserApiAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpRestController {

    private final AuthAdapter authAdapter;
    private final UserApiAdapter userApiAdapter;

    @PostMapping("/email")
    public ResponseEntity<CertificationNumberResponse> requestCertificationNumber(@RequestBody EmailCertificationRequest request) {

        System.out.println(request.getUserEmail());

        return authAdapter.requestCertificationNumber(request);
    }

}
