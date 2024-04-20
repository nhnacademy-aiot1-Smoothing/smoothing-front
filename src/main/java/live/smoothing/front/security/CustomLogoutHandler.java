package live.smoothing.front.security;

import live.smoothing.front.adapter.AuthAdapter;
import live.smoothing.front.dto.RefreshTokenRequest;
import live.smoothing.front.token.ThreadLocalToken;
import live.smoothing.front.token.entity.TokenWithType;
import live.smoothing.front.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
  
    private final AuthAdapter authAdapter;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        Cookie accessToken = CookieUtil.getCookieByName(request.getCookies(), "smoothing_accessToken");
        Cookie refreshToken = CookieUtil.getCookieByName(request.getCookies(), "smoothing_refreshToken");
        if(accessToken != null) {
            accessToken.setMaxAge(0);
            response.addCookie(accessToken);
        }
        if(refreshToken != null) {
            refreshToken.setMaxAge(0);
            response.addCookie(refreshToken);
        }
        if (refreshToken != null) {
            ThreadLocalToken.TOKEN.set(CookieUtil.decodeTokenWithType(Objects.requireNonNull(accessToken).getValue()));
            TokenWithType tokenWithType = CookieUtil.decodeTokenWithType(refreshToken.getValue());
            authAdapter.logout(new RefreshTokenRequest(tokenWithType.getToken()));
        }
    }
}
