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

        if(isExpired(accessToken)){
            accessToken = reIssueAccessToken(accessToken);
        }

        UsernamePasswordAuthenticationToken token = getAuthenticationToken(accessToken);

        return new SecurityContextImpl(token);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String accessToken) {

        UsernamePasswordAuthenticationToken authenticationToken = null;
        if(accessToken==null){
            return authenticationToken;
        }


        try {
            authenticationToken = new UsernamePasswordAuthenticationToken(JwtUtil.getUserId(accessToken), null, JwtUtil.getRoles(accessToken).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        } catch (Exception e){
            log.debug("token Value error");
        }
        return authenticationToken;
    }

    private String reIssueAccessToken(String accessToken) {

        String reIssuedAccessToken = null;
        if(accessToken==null){
            return reIssuedAccessToken;
        }


        return null;
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

    private boolean isExpired(String accessToken){
        return false;
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

    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }
}