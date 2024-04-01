package live.smoothing.front.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenHandlerInterceptor implements HandlerInterceptor {
    private final String ACCESS_TOKEN = "smoothing-accessToken";
    private final String REFRESH_TOKEN = "smoothing-refreshToken";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if(ACCESS_TOKEN.equals(c.getName())) {
                    response.addHeader(c.getName(), c.getValue());
                }
                if(REFRESH_TOKEN.equals(c.getName())) {
                    response.addHeader(c.getName(), c.getValue());
                }
            }
        }
        return true;
    }
}
