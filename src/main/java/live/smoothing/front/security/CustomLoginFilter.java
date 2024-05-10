package live.smoothing.front.security;

import live.smoothing.front.util.CookieUtil;
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

/**
 * @author 우혜승
 * @see CustomAuthenticationToken 를 통해 인증을 진행하는 Filter
 * @see HttpServletRequest 에서 userId, userPassword를 받아 CustomAuthenticationToken 을 생성하고
 * @see AuthenticationManager 를 통해 인증을 진행한다.
 * @see org.springframework.security.authentication.ProviderManager 에서 인증을 시도하고
 * @see CustomAuthenticationToken 을 반환받아 로그인 성공 시 accessToken, refreshToken을 쿠키에 저장한다.
 * @see HttpServletResponse 를 통해 메인 페이지로 리다이렉트한다.
 */
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * HttpServletRequest 에서 userId, userPassword 를 받아 CustomAuthenticationToken 을 생성하고
     * AuthenticationManager 를 통해 인증을 시도한다.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return userId 와 userPassword 가 담긴 CustomAuthenticationToken
     * @throws AuthenticationException 인증 실패 시 예외
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String userId = obtainUsername(request);
        String userPassword = obtainPassword(request);

        CustomAuthenticationToken token = new CustomAuthenticationToken(userId, userPassword, null);
        return authenticationManager.authenticate(token);
    }

    /**
     * ProviderManager 에서 인증을 시도하고 성공시 accessToken, refreshToken 을 쿠키에 저장한다.
     *
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param chain      FilterChain
     * @param authResult CustomAuthenticationProvider 에서 인증 후 accessToken 및 refreshToken 을 담은 CustomAuthenticationToken
     * @throws IOException sendRedirect 실패 시 예외
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        CustomAuthenticationToken token = (CustomAuthenticationToken) authResult;
        String tokenType = token.getLoginResponse().getTokenType();
        String accessToken = token.getLoginResponse().getAccessToken();
        String refreshToken = token.getLoginResponse().getRefreshToken();
        Cookie accessCookie = CookieUtil.createAccessTokenCookie(tokenType, accessToken);
        Cookie refreshCookie = CookieUtil.createRefreshTokenCookie(tokenType, refreshToken);
        accessCookie.setPath("/");
        refreshCookie.setPath("/");
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        SecurityContext securityContext = new SecurityContextImpl(token);
        SecurityContextHolder.setContext(securityContext);
        response.sendRedirect("/");
    }

    /**
     * 인증 실패 시 로그인 페이지로 리다이렉트한다.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param failed   AuthenticationException
     * @throws IOException sendRedirect 실패 시 예외
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        //todo 에러처리
        response.sendRedirect("/login");
    }
}
