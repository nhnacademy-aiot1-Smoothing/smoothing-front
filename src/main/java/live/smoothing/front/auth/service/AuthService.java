package live.smoothing.front.auth.service;

import live.smoothing.front.auth.dto.email.EmailCertificationRequest;
import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.auth.dto.email.VerificationRequest;
import live.smoothing.front.auth.dto.login.LoginRequest;
import live.smoothing.front.auth.dto.login.LoginResponse;
import live.smoothing.front.auth.dto.token.RefreshTokenRequest;
import live.smoothing.front.auth.dto.token.ReissueResponse;

public interface AuthService {

    LoginResponse doLogin(LoginRequest loginRequest);

    ReissueResponse refreshToken(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);

    MessageResponse requestCertificationNumber(EmailCertificationRequest request);

    MessageResponse verifyCertificationNumber(VerificationRequest request);

    LoginResponse doOAuthLogin(String userId);

}
