package live.smoothing.front.util;

import javax.servlet.http.Cookie;
import java.util.Arrays;

public class CookieUtil {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "smoothing_accessToken";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "smoothing_refreshToken";

    public static Cookie getCookieByName(Cookie[] cookies, String name) {
        return Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    public static Cookie createAccessTokenCookie(String tokenType, String accessToken) {
        return new Cookie(ACCESS_TOKEN_COOKIE_NAME, createTokenWithType(tokenType, accessToken));
    }

    public static Cookie createRefreshTokenCookie(String tokenType, String refreshToken) {
        return new Cookie(REFRESH_TOKEN_COOKIE_NAME, createTokenWithType(tokenType, refreshToken));
    }

    private static String createTokenWithType(String tokenType, String token) {
        return tokenType + " " + token;
    }
}
