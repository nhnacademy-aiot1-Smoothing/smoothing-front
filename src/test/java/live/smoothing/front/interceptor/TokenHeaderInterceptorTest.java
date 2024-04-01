package live.smoothing.front.interceptor;

import feign.RequestTemplate;
import live.smoothing.front.token.ThreadLocalToken;
import live.smoothing.front.token.entity.TokenWithType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenRequestInterceptorTest {

    private TokenRequestInterceptor interceptor;

    @BeforeEach
    void setUp() {
        interceptor = new TokenRequestInterceptor();
    }

    @Test
    @DisplayName("ThreadLocal에 토큰이 있으면 헤더에 추가한다.")
    void addTokenHeaderWhenTokenPresent() {

        TokenWithType token = new TokenWithType("Bearer", "123456");
        ThreadLocalToken.TOKEN.set(token);

        RequestTemplate requestTemplate = new RequestTemplate();

        interceptor.apply(requestTemplate);

        assertEquals("Bearer 123456", requestTemplate.headers().get("Authorization").iterator().next());
    }

    @Test
    @DisplayName("ThreadLocal에 토큰이 없으면 헤더에 추가하지 않는다.")
    void addTokenHeaderWhenTokenNotPresent() {

        RequestTemplate requestTemplate = new RequestTemplate();

        interceptor.apply(requestTemplate);

        assertFalse(requestTemplate.headers().containsKey("Authorization"));
    }
}