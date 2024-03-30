package live.smoothing.front.security;

import live.smoothing.front.adapter.AuthAdaptor;
import live.smoothing.front.auth.dto.LoginRequest;
import live.smoothing.front.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthAdaptor authAdaptor;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        ResponseEntity<LoginResponse> response;
        try {
            response = authAdaptor.doLogin(new LoginRequest(authentication.getName(), (String) authentication.getCredentials()));
        } catch (Exception e) {
            //todo feign 200 아닐 경우 에러 처리돼서 따로 뭔가 해줘야함
            throw new InternalAuthenticationServiceException("Internal Server Error");
        }
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new BadCredentialsException("Fail to Login");
        }
        return new CustomAuthenticationToken(authentication.getName(), (String) authentication.getCredentials(), response.getBody());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
