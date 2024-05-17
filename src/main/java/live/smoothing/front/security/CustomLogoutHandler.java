package live.smoothing.front.security;

import live.smoothing.front.adapter.AuthAdapter;
import live.smoothing.front.auth.dto.token.RefreshTokenRequest;
import live.smoothing.front.token.ThreadLocalToken;
import live.smoothing.front.token.entity.TokenWithType;
import live.smoothing.front.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final AuthAdapter authAdapter;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        Cookie accessToken = CookieUtil.getCookieByName(request.getCookies(), "smoothing_accessToken");
        Cookie refreshToken = CookieUtil.getCookieByName(request.getCookies(), "smoothing_refreshToken");
        if (accessToken != null) {
            accessToken.setMaxAge(0);
            accessToken.setPath("/");
            response.addCookie(accessToken);
        }
        if (refreshToken != null) {
            refreshToken.setMaxAge(0);
            refreshToken.setPath("/");
            response.addCookie(refreshToken);
        }

        try {
            if (refreshToken != null) {
                ThreadLocalToken.TOKEN.set(CookieUtil.decodeTokenWithType(Objects.requireNonNull(accessToken).getValue()));
                TokenWithType tokenWithType = CookieUtil.decodeTokenWithType(refreshToken.getValue());
                authAdapter.logout(new RefreshTokenRequest(tokenWithType.getToken()));
            }
        } catch (Exception e) {
            log.error("로그아웃 중 에러 발생", e);
            //todo 무언가
        }
    }
}
