package live.smoothing.front.util;

import live.smoothing.front.token.entity.TokenWithType;

import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import static java.util.Base64.getUrlEncoder;

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
        return URLEncoder.encode(tokenType + " " + token, StandardCharsets.UTF_8);
    }

    private static TokenWithType decodeTokenWithType(String encodedTokenWithType) {
        String tokenWithType = String.valueOf(URLDecoder.decode(encodedTokenWithType, StandardCharsets.UTF_8));
        String[] split = tokenWithType.split(" ");
        return new TokenWithType(split[0], split[1]);
    }
}
