package live.smoothing.front.auth.service.impl;

import feign.FeignException;
import live.smoothing.front.adapter.AuthAdapter;
import live.smoothing.front.auth.dto.email.EmailCertificationRequest;
import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.auth.dto.email.VerificationRequest;
import live.smoothing.front.auth.dto.login.LoginRequest;
import live.smoothing.front.auth.dto.login.LoginResponse;
import live.smoothing.front.auth.dto.token.RefreshTokenRequest;
import live.smoothing.front.auth.dto.token.ReissueResponse;
import live.smoothing.front.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthAdapter authAdapter;


    @Override
    public LoginResponse doLogin(LoginRequest loginRequest) {

        return authAdapter.doLogin(loginRequest).getBody();
    }

    @Override
    public ReissueResponse refreshToken(RefreshTokenRequest request) {

        return authAdapter.refreshToken(request).getBody();
    }

    @Override
    public void logout(RefreshTokenRequest request) {

        authAdapter.logout(request).getBody();
    }

    @Override
    public MessageResponse requestCertificationNumber(EmailCertificationRequest request) {

        return authAdapter.requestCertificationNumber(request).getBody();
    }

    @Override
    public MessageResponse verifyCertificationNumber(VerificationRequest request) {

        try {
            return authAdapter.verifyCertificationNumber(request).getBody();
        } catch(FeignException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }


    }

    @Override
    public LoginResponse doOAuthLogin(String userId) {
        return authAdapter.doOAuthLogin(userId).getBody();
    }
}
