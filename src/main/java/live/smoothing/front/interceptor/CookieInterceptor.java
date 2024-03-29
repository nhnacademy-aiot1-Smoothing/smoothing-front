package live.smoothing.front.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<String> accessToken = new ThreadLocal<>();
    public static final ThreadLocal<String> refreshToken = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("accessToken".equals(cookie.getName())) {
                    accessToken.set(cookie.getValue());
                }
                if("refreshToken".equals(cookie.getName())) {
                    refreshToken.set(cookie.getValue());
                }
            }
        }
        return true;
    }
}