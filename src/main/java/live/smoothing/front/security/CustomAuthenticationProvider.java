package live.smoothing.front.security;

import feign.FeignException;
import live.smoothing.front.adapter.AuthAdapter;
import live.smoothing.front.adapter.UserApiAdapter;
import live.smoothing.front.auth.dto.login.LoginRequest;
import live.smoothing.front.auth.dto.login.LoginResponse;
import live.smoothing.front.user.dto.response.UserStateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

/**
 * @author 우혜승
 * @author 우혜승
 * @see CustomAuthenticationToken 를 통해 인증을 진행하는 Provider
 * @see CustomAuthenticationToken 에서 userId, userPassword를 받아 인증을 진행하고
 * @see AuthAdapter 를 통해 인증을 진행한다.
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthAdapter authAdapter;
    private final UserApiAdapter adapter;

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

            UserStateResponse userStateResponse = adapter.getUserState(authentication.getName());

            if (Objects.equals(userStateResponse.getUserState(), "NOT_APPROVED")) {

                throw new DisabledException("계정이 승인되지 않았습니다.");

            } else if (Objects.equals(userStateResponse.getUserState(), "WITHDRAWAL")) {

                throw new DisabledException("탈퇴된 회원입니다.");
            }


            response = authAdapter.doLogin(new LoginRequest(authentication.getName(), (String) authentication.getCredentials()));
        } catch (FeignException e) {
            log.error("{} : {}", e.status(), e.contentUTF8());
            if (e.status() == 404) {
                throw new BadCredentialsException("유저를 찾을 수 없습니다.");
            } else if (e.status() == 401) {
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
            } else if (e.status() != 200) {
                throw new BadCredentialsException("기타 오류가 발생했습니다.");
            } else {
                throw new InternalAuthenticationServiceException("내부 서버 오류가 발생했습니다.");
            }
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
