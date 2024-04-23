package live.smoothing.front.security;

import live.smoothing.front.adapter.AuthAdapter;
import live.smoothing.front.auth.dto.login.LoginRequest;
import live.smoothing.front.auth.dto.login.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author 우혜승
 * @see CustomAuthenticationToken 를 통해 인증을 진행하는 Provider
 * @see CustomAuthenticationToken 에서 userId, userPassword를 받아 인증을 진행하고
 * @see AuthAdapter 를 통해 인증을 진행한다.
 *
 * @author 우혜승
 */
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthAdapter authAdapter;

    /**
     * @param authentication the inheritor of CustomAuthenticationToken
     * @return the inheritor of CustomAuthenticationToken
     * @throws AuthenticationException 인증 서버 에러, 로그인 실패 등의 예외
     * @see CustomAuthenticationToken 를 받아서 인증을 시도한다.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        ResponseEntity<LoginResponse> response;
        try {
            response = authAdapter.doLogin(new LoginRequest(authentication.getName(), (String) authentication.getCredentials()));
        } catch (Exception e) {
            //todo feign 200 아닐 경우 에러 처리돼서 따로 뭔가 해줘야함
            throw new InternalAuthenticationServiceException("Internal Server Error");
        }
        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new BadCredentialsException("Fail to Login");
        }
        return new CustomAuthenticationToken(authentication.getName(), (String) authentication.getCredentials(), response.getBody());
    }

    /**
     * 인자로 받은 authentication 이 CustomAuthenticationToken 의 인스턴스인지 확인한다.
     *
     * @param authentication the inheritor of CustomAuthenticationToken
     * @return true if the authentication is the inheritor of CustomAuthenticationToken
     */
    @Override
    public boolean supports(Class<?> authentication) {

        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
