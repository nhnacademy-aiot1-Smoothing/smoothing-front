package live.smoothing.front.security;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@Slf4j
public class CustomSecurityContextRepository implements SecurityContextRepository {

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {

        String accessToken = getAccessToken(requestResponseHolder.getRequest().getCookies());

        UsernamePasswordAuthenticationToken token = null;

        try {
            token = getAuthenticationToken(accessToken);
        } catch (JsonProcessingException e) {
            log.debug("Token Value Error");
            Cookie expireCookie = new Cookie("smoothing_accessToken", null);
            expireCookie.setMaxAge(0);
            expireCookie.setPath("/");
            requestResponseHolder.getResponse().addCookie(expireCookie);
        }

        return new SecurityContextImpl(token);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String accessToken) throws JsonProcessingException {

        UsernamePasswordAuthenticationToken authenticationToken = null;
        if (accessToken == null) {
            return authenticationToken;
        }


        authenticationToken = new UsernamePasswordAuthenticationToken(JwtUtil.getUserId(accessToken), null, JwtUtil.getRoles(accessToken).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return authenticationToken;
    }

    private String getAccessToken(Cookie[] cookies) {

        String accessToken = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("smoothing_accessToken")) {
                    accessToken = c.getValue();
                }
            }
        }
        return accessToken;
    }

    @Override
    public Supplier<SecurityContext> loadContext(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return SecurityContextHolder::createEmptyContext;
        }
        String accessToken = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("smoothing_accessToken")) {
                accessToken = c.getValue();
            }
        }

        UsernamePasswordAuthenticationToken token;
        try {
            String finalAccessToken = accessToken;
            token = new UsernamePasswordAuthenticationToken(JwtUtil.getUserId(finalAccessToken), null, JwtUtil.getRoles(finalAccessToken).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            return SecurityContextHolder::createEmptyContext;
        }
        Supplier<SecurityContext> securityContext = () -> new SecurityContextImpl(token);
        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(context.getAuthentication());
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }
}
