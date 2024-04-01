package live.smoothing.front.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.adapter.AuthAdaptor;
import live.smoothing.front.dto.RefreshTokenRequest;
import live.smoothing.front.dto.ReissueResponse;
import live.smoothing.front.util.CookieUtil;
import live.smoothing.front.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReissueJwtTokenInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "smoothing_accessToken";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "smoothing_refreshToken";

    private final AuthAdaptor authAdaptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        Cookie accessToken = CookieUtil.getCookieByName(cookies, ACCESS_TOKEN_COOKIE_NAME);
        Cookie refreshToken = CookieUtil.getCookieByName(cookies, REFRESH_TOKEN_COOKIE_NAME);

        if (Objects.isNull(accessToken) || Objects.isNull(refreshToken)) {
            return true;
        }

        if (requireReissue(accessToken.getValue())) {
            RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken.getValue());
            ResponseEntity<ReissueResponse> tokenResponse = authAdaptor.refreshToken(refreshTokenRequest);
            String newAccessToken = Objects.requireNonNull(tokenResponse.getBody()).getAccessToken();
            Cookie newAccessTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, newAccessToken);
            response.addCookie(newAccessTokenCookie);
        }

        return true;
    }

    private boolean requireReissue(String accessToken) throws JsonProcessingException {
        Long expireTime = JwtUtil.getExpireTime(accessToken);
        long now = new Date().getTime();

        return (now / 1000L) > expireTime;
    }
}
