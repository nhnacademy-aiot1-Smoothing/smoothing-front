package live.smoothing.front.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import live.smoothing.front.token.ThreadLocalToken;
import org.springframework.stereotype.Component;

/**
 * 토큰을 요청 헤더에 추가하는 RequestInterceptor 구현 클래스
 *
 * @author : 신민석
 */
@Component
public class TokenRequestInterceptor implements RequestInterceptor {

    /**
     * 토큰을 요청 헤더에 추가하는 메서드
     * 1. ThreadLocalToken에서 토큰을 가져온다.
     * 2. 템플릿에 토큰을 추가한다.
     * 3. ThreadLocalToken에서 토큰을 제거한다.
     *
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {

        String tokenType = ThreadLocalToken.TOKEN.get().getTokenType();
        String token = ThreadLocalToken.TOKEN.get().getToken();

        String authorizationToken = tokenType + " " + token;

        requestTemplate.header("Authorization", authorizationToken);

        ThreadLocalToken.TOKEN.remove();
    }
}

