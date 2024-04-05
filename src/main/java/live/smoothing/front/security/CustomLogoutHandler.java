package live.smoothing.front.security;

import live.smoothing.front.adapter.AuthAdaptor;
import live.smoothing.front.dto.RefreshTokenRequest;
import live.smoothing.front.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final AuthAdaptor authAdaptor;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie accessToken = CookieUtil.getCookieByName(request.getCookies(), "smoothing_accessToken");
        Cookie refreshToken = CookieUtil.getCookieByName(request.getCookies(), "smoothing_refreshToken");
        if (accessToken != null) {
            accessToken.setMaxAge(0);
            response.addCookie(accessToken);
        }
        if (refreshToken != null) {
            refreshToken.setMaxAge(0);
            response.addCookie(refreshToken);
        }
        if (refreshToken != null) {
            authAdaptor.logout(new RefreshTokenRequest(refreshToken.getValue()));
        }
    }
}
