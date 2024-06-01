package live.smoothing.front.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.auth.dto.login.LoginResponse;
import live.smoothing.front.util.JwtUtil;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author 우혜승
 * @see CustomLoginFilter 및 CustomAuthenticationProvider 에서 사용하는 CustomAuthenticationToken
 * @see AbstractAuthenticationToken 을 상속받아 구현
 * @see CustomLoginFilter 에서는 로그인 시도 시 사용자의 id, password를 받아 CustomAuthenticationToken 을 생성하고
 * @see CustomAuthenticationProvider 에서는 CustomAuthenticationToken 을 받아 인증을 시도한다.
 * @see CustomAuthenticationToken 은 사용자의 id, password, 로그인 응답(accessToken, refreshToken)을 가지고 있다.
 */
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final String userId;
    private final String userPassword;
    @Getter
    private final LoginResponse loginResponse;

    /**
     * @param userId       사용자의 id
     * @param userPassword 사용자의 password
     * @param response     로그인 응답(accessToken, refreshToken)
     */
    public CustomAuthenticationToken(String userId, String userPassword, LoginResponse response) {

        super(null);
        this.userId = userId;
        this.userPassword = userPassword;
        this.loginResponse = response;
    }

    /**
     * @return accessToken 을 통해 얻은 사용자의 권한
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        try {
            return JwtUtil.getRoles(loginResponse.getAccessToken()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return 사용자의 password
     */
    @Override
    public Object getCredentials() {

        return userPassword;
    }

    /**
     * @return 사용자의 id
     */
    @Override
    public Object getPrincipal() {

        return userId;
    }

    /**
     * @return 사용자의 id, password 의 일치여부
     */
    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof CustomAuthenticationToken)) {
            return false;
        }
        CustomAuthenticationToken token = (CustomAuthenticationToken) obj;
        return getPrincipal().equals(token.getPrincipal())
                && getCredentials().equals(token.getCredentials());
    }
}
