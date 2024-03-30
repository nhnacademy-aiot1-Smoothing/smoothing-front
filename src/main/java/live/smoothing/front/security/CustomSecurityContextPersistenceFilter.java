package live.smoothing.front.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class CustomSecurityContextPersistenceFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies == null) {
            ((HttpServletResponse) response).sendRedirect("/login");
            return;
        }
        String accessToken = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("smoothing_accessToken")) {
                accessToken = c.getValue();
            }
        }

        UsernamePasswordAuthenticationToken token = null;
        try {
            String finalAccessToken = accessToken;
            token = new UsernamePasswordAuthenticationToken(JwtUtil.getUserId(finalAccessToken), null, JwtUtil.getRoles(finalAccessToken).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            ((HttpServletResponse) response).sendRedirect("/login");
            return;
        }
        SecurityContext securityContext = new SecurityContextImpl(token);
        SecurityContextHolder.setContext(securityContext);
        chain.doFilter(request, response);
    }
}
