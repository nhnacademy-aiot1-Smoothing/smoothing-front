package live.smoothing.front.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if("accessToken".equals(c.getName())) {
                    response.addHeader(c.getName(), c.getValue());
                }
                if("refreshToken".equals(c.getName())) {
                    response.addHeader(c.getName(), c.getValue());
                }
            }
        }
        return true;
    }
}
