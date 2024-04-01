package live.smoothing.front.util;

import javax.servlet.http.Cookie;
import java.util.Arrays;

/**
 * 쿠키 관련 유틸 클래스
 *
 * @author 박영준
 */
public class CookieUtil {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "smoothing_accessToken";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "smoothing_refreshToken";

    /**
     * 쿠키 배열에서 쿠키 이름을 통해 쿠키를 찾아 반환한다.
     * 쿠키를 찾지 못하면 null을 반환한다.
     *
     * @param cookies 사용자 요청 쿠키 배열
     * @param name 찾고자 하는 쿠키 이름
     * @return 이름에 맞는 쿠키
     */
    public static Cookie getCookieByName(Cookie[] cookies, String name) {
        return Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * JWT Access Token을 생성하는 메서드
     *
     * @param tokenType JWT 토큰 타입
     * @param accessToken 문자열로 되어있는 JWT Access Token
     * @return Access Token을 담은 쿠키
     */
    public static Cookie createAccessTokenCookie(String tokenType, String accessToken) {
        return new Cookie(ACCESS_TOKEN_COOKIE_NAME, createTokenWithType(tokenType, accessToken));
    }

    /**
     * JWT Refresh Token을 생성하는 메서드
     *
     * @param tokenType JWT 토큰 타입
     * @param refreshToken 문자열로 되어있는 JWT Refresh Token
     * @return Refresh Token을 담은 쿠키
     */
    public static Cookie createRefreshTokenCookie(String tokenType, String refreshToken) {
        return new Cookie(REFRESH_TOKEN_COOKIE_NAME, createTokenWithType(tokenType, refreshToken));
    }

    private static String createTokenWithType(String tokenType, String token) {
        return tokenType + " " + token;
    }
}
