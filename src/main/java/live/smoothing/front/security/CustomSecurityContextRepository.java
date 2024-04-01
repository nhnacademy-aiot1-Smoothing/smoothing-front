package live.smoothing.front.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.token.entity.TokenWithType;
import live.smoothing.front.util.CookieUtil;
import live.smoothing.front.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @see SecurityContextRepository 를 구현한 CustomSecurityContextRepository
 * @see Cookie 기반의 jwt 인증을 제공한다.
 *
 */
@Slf4j
public class CustomSecurityContextRepository implements SecurityContextRepository {

    private final static String ACCESS_TOKEN_COOKIE_NAME = "smoothing_accessToken";

    /**
     * 사용자의 요청 쿠키에서 accessToken을 가져와서 CustomAuthenticationToken을 생성한다.
     *
     * @param requestResponseHolder HttpRequestResponseHolder
     * @return UsernamePasswordAuthenticationToken
     */
    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {

        Cookie cookie = CookieUtil.getCookieByName(requestResponseHolder.getRequest().getCookies(), ACCESS_TOKEN_COOKIE_NAME);

        String accessToken = null;

        if(cookie!=null){
            accessToken = cookie.getValue();
        }

        UsernamePasswordAuthenticationToken token = null;

        try {
            token = getAuthenticationToken(accessToken);
        } catch (Exception e) {
            log.debug("Token Value Error");
            Cookie expireCookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, null);
            expireCookie.setMaxAge(0);
            expireCookie.setPath("/");
            requestResponseHolder.getResponse().addCookie(expireCookie);
        }

        return new SecurityContextImpl(token);
    }

    /**
     * accessToken을 통해 사용자의 id, 권한을 가져와서 UsernamePasswordAuthenticationToken을 생성한다.
     *
     * @param accessToken 사용자의 accessToken
     * @return UsernamePasswordAuthenticationToken
     * @throws RuntimeException CookieUtil.decodeTokenWithType 에서 발생하는 예외
     * @throws JsonProcessingException accessToken을 decode할 때 예외
     */
    private UsernamePasswordAuthenticationToken getAuthenticationToken(String accessToken) throws RuntimeException, JsonProcessingException {

        UsernamePasswordAuthenticationToken authenticationToken = null;
        if (accessToken == null) {
            return authenticationToken;
        }

        TokenWithType tokenWithType = CookieUtil.decodeTokenWithType(accessToken);


        authenticationToken = new UsernamePasswordAuthenticationToken(JwtUtil.getUserId(tokenWithType.getToken()), null, JwtUtil.getRoles(tokenWithType.getToken()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return authenticationToken;
    }

    /**
     * 만들어 뒀지만 사용하지 않음
     * @param request the {@link HttpServletRequest} to load the {@link SecurityContext}
     *
     * @return SecurityContext
     */
    @Override
    public Supplier<SecurityContext> loadContext(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return SecurityContextHolder::createEmptyContext;
        }
        String accessToken = CookieUtil.getCookieByName(cookies, ACCESS_TOKEN_COOKIE_NAME).getValue();

        UsernamePasswordAuthenticationToken token;
        try {
            String finalAccessToken = CookieUtil.decodeTokenWithType(accessToken).getToken();
            token = new UsernamePasswordAuthenticationToken(JwtUtil.getUserId(finalAccessToken), null, JwtUtil.getRoles(finalAccessToken).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            return SecurityContextHolder::createEmptyContext;
        }
        return () -> new SecurityContextImpl(token);
    }

    /**
     * 요청 처리 후에 SecurityContext 를 저장한다.
     * @param context SecurityContext
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(context.getAuthentication());
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }
}
