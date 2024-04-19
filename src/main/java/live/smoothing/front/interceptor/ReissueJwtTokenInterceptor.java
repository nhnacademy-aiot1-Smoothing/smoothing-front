package live.smoothing.front.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.auth.adapter.AuthAdapter;
import live.smoothing.front.dto.RefreshTokenRequest;
import live.smoothing.front.dto.ReissueResponse;
import live.smoothing.front.token.ThreadLocalToken;
import live.smoothing.front.token.entity.TokenWithType;
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

/**
 * JWT Access Token 재발급 인터셉터
 *
 * @author 박영준
 */
@Component
@RequiredArgsConstructor
public class ReissueJwtTokenInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "smoothing_accessToken";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "smoothing_refreshToken";

    private final AuthAdapter authAdapter;

    /**
     * 사용자 요청 쿠키에서 Access Token을 꺼내와 만료시간 검사 후 토큰 재발급을 처리하는 메서드
     *
     * @param request  사용자 요청 객체
     * @param response 사용자 응답 객체
     * @param handler  핸들러 매핑을 통해 해당 요청을 처리할 핸들러 객체
     * @return 요청 처리 지속 여부
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Cookie[] cookies = request.getCookies();
        Cookie encodedAccessToken = CookieUtil.getCookieByName(cookies, ACCESS_TOKEN_COOKIE_NAME);
        Cookie encodedRefreshToken = CookieUtil.getCookieByName(cookies, REFRESH_TOKEN_COOKIE_NAME);

        if(Objects.isNull(encodedAccessToken) || Objects.isNull(encodedRefreshToken)) {
            return true;
        }

        TokenWithType accessToken = CookieUtil.decodeTokenWithType(encodedAccessToken.getValue());
        TokenWithType refreshToken = CookieUtil.decodeTokenWithType(encodedRefreshToken.getValue());

        try {
            if(requireReissue(accessToken.getToken())) {
                RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken.getToken());
                ResponseEntity<ReissueResponse> tokenResponse = authAdapter.refreshToken(refreshTokenRequest);
                ReissueResponse responseBody = Objects.requireNonNull(tokenResponse.getBody());

                ThreadLocalToken.TOKEN.set(new TokenWithType(responseBody.getTokenType(), responseBody.getAccessToken()));

                String newAccessToken = Objects.requireNonNull(responseBody).getAccessToken();
                Cookie newAccessTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, newAccessToken);

                response.addCookie(newAccessTokenCookie);
            }
        } catch(JsonProcessingException e) {
            throw new RuntimeException();
        }

        return true;
    }

    /**
     * 현재 시간과 JWT Token의 만료 시간을 비교하여 재발급 여부를 판단하는 메서드
     *
     * @param accessToken 문자열 JWT Token
     * @return 액세스 토큰이 만료되었으면 true, 아니면 false
     * @throws JsonProcessingException json 형태가 아니면 파싱 중 발생하는 예외
     */
    private boolean requireReissue(String accessToken) throws JsonProcessingException {

        Long expireTime = JwtUtil.getExpireTime(accessToken);
        long now = new Date().getTime();

        return (now / 1000L) > expireTime;
    }
}
