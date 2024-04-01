package live.smoothing.front.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;

class TokenHandlerInterceptorTest {

    private TokenRequestInterceptor interceptor;
    private final String accessToken = "smoothing-accessToken";
    private final String refreshToken = "smoothing-refreshToken";
    @BeforeEach
    void setUp() {
        interceptor = new TokenRequestInterceptor();

    }

    @Test
    @DisplayName("쿠키에 토큰이 있으면 헤더에 추가한다.")
    void addTokenHeaderWhenCookiesPresent() throws Exception {


        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setCookies(new Cookie(accessToken, "someAccessToken"), new Cookie(refreshToken, "someRefreshToken"));

        interceptor.preHandle(request, response, null);

        assertEquals("someAccessToken", response.getHeader(accessToken));
        assertEquals("someRefreshToken", response.getHeader(refreshToken));
    }

    @Test
    @DisplayName("쿠키에 토큰이 없으면 헤더에 추가하지 않는다.")
    void doNotAddTokenHeaderWhenCookiesNotPresent() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        interceptor.preHandle(request, response, null);

        assertNull(response.getHeader(accessToken));
        assertNull(response.getHeader(refreshToken));
    }
}