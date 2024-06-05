package live.smoothing.front.security;

import live.smoothing.front.auth.dto.login.LoginResponse;
import live.smoothing.front.auth.service.AuthService;
import live.smoothing.front.user.service.UserService;
import live.smoothing.front.util.CookieUtil;
import live.smoothing.front.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomOAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "smoothing_accessToken";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "smoothing_refreshToken";

    private final UserService userService;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            System.out.println("hahaha");
            System.out.println(authentication.getPrincipal());
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            request.getSession(true).invalidate();
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            if (token.getAuthorizedClientRegistrationId().equals("github")) {
                if (hasToken(request)) {
                    if (userService.getUserOAuthByProviderKey_providerName(token.getPrincipal().getAttribute("id").toString(), "github")) {
                        response.sendRedirect("/");
                        return;
                    }
                    userService.saveUserOAuth(token.getPrincipal().getAttribute("id").toString(), "github", JwtUtil.getUserId(CookieUtil.getCookieByName(request.getCookies(), ACCESS_TOKEN_COOKIE_NAME).getValue()));
                    response.sendRedirect("/mypage");
                } else {
                    if (!userService.getUserOAuthByProviderKey_providerName(token.getPrincipal().getAttribute("id").toString(), "github")) {
                        response.sendRedirect("/");
                        return;
                    }
                    String userId = userService.getUserIdByProviderKey_providerName(token.getPrincipal().getAttribute("id").toString(), "github");
                    LoginResponse loginResponse = authService.doOAuthLogin(userId);
                    Cookie accessCookie = CookieUtil.createAccessTokenCookie("Bearer", loginResponse.getAccessToken());
                    Cookie refreshCookie = CookieUtil.createRefreshTokenCookie("Bearer", loginResponse.getRefreshToken());
                    accessCookie.setPath("/");
                    refreshCookie.setPath("/");
                    response.addCookie(accessCookie);
                    response.addCookie(refreshCookie);

                    SecurityContext securityContext = new SecurityContextImpl(token);
                    SecurityContextHolder.setContext(securityContext);
                    response.sendRedirect("/");
                    return;
                }
            }else if (token.getAuthorizedClientRegistrationId().equals("kakao")){
                if (hasToken(request)) {
                    if (userService.getUserOAuthByProviderKey_providerName(token.getPrincipal().getAttribute("id").toString(), "kakao")) {
                        response.sendRedirect("/");
                        return;
                    }
                    userService.saveUserOAuth(token.getPrincipal().getAttribute("id").toString(), "kakao", JwtUtil.getUserId(CookieUtil.getCookieByName(request.getCookies(), ACCESS_TOKEN_COOKIE_NAME).getValue()));
                    response.sendRedirect("/mypage");
                } else {
                    if (!userService.getUserOAuthByProviderKey_providerName(token.getPrincipal().getAttribute("id").toString(), "kakao")) {
                        response.sendRedirect("/");
                        return;
                    }
                    String userId = userService.getUserIdByProviderKey_providerName(token.getPrincipal().getAttribute("id").toString(), "kakao");
                    LoginResponse loginResponse = authService.doOAuthLogin(userId);
                    Cookie accessCookie = CookieUtil.createAccessTokenCookie("Bearer", loginResponse.getAccessToken());
                    Cookie refreshCookie = CookieUtil.createRefreshTokenCookie("Bearer", loginResponse.getRefreshToken());
                    accessCookie.setPath("/");
                    refreshCookie.setPath("/");
                    response.addCookie(accessCookie);
                    response.addCookie(refreshCookie);

                    SecurityContext securityContext = new SecurityContextImpl(token);
                    SecurityContextHolder.setContext(securityContext);
                    response.sendRedirect("/");
                    return;
                }
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private boolean hasToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        return CookieUtil.getCookieByName(cookies, ACCESS_TOKEN_COOKIE_NAME) != null;
    }
}
