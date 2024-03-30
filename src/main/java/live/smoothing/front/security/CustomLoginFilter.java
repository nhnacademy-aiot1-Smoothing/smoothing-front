package live.smoothing.front.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String userId = obtainUsername(request);
        String userPassword = obtainPassword(request);

        CustomAuthenticationToken token = new CustomAuthenticationToken(userId, userPassword, null);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomAuthenticationToken token = (CustomAuthenticationToken) authResult;
        String accessToken = token.getLoginResponse().getAccessToken();
        String refreshToken = token.getLoginResponse().getRefreshToken();
//        System.out.println("fsdfsdfsfsdf");
//        System.out.println(Arrays.toString(Base64.getEncoder().encode(accessToken.getBytes()))); 이거 안 담김
        Cookie accessCookie = new Cookie("smoothing_accessToken", accessToken);
        Cookie refreshCookie = new Cookie("smoothing_refreshToken", refreshToken);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        SecurityContext securityContext = new SecurityContextImpl(token);
        SecurityContextHolder.setContext(securityContext);
        response.sendRedirect("/");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.sendRedirect("/login");
    }
}
