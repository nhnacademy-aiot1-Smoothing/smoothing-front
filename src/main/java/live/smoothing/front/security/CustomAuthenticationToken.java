package live.smoothing.front.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.auth.dto.LoginResponse;
import live.smoothing.front.util.JwtUtil;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final String userId;
    private final String userPassword;
    @Getter
    private final LoginResponse loginResponse;

    public CustomAuthenticationToken(String userId, String userPassword, LoginResponse response) {

        super(null);
        this.userId = userId;
        this.userPassword = userPassword;
        this.loginResponse = response;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        try {
            return JwtUtil.getRoles(loginResponse.getAccessToken()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getCredentials() {

        return userPassword;
    }

    @Override
    public Object getPrincipal() {

        return userId;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof CustomAuthenticationToken)){
            return false;
        }
        CustomAuthenticationToken token = (CustomAuthenticationToken) obj;
        return getPrincipal().equals(token.getPrincipal())
                && getCredentials().equals(token.getCredentials());
    }
}
