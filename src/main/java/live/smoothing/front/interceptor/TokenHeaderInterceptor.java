package live.smoothing.front.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        if (CookieInterceptor.accessToken.get() != null) {
            template.header("accessToken", CookieInterceptor.accessToken.get());
        }
        if (CookieInterceptor.refreshToken.get() != null) {
            template.header("refreshToken", CookieInterceptor.refreshToken.get());
        }
    }
}
