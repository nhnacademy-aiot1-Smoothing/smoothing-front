package live.smoothing.front.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.tomcat.util.json.JSONParserConstants.ZERO;

@Component
public class CustomHttpSessionOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public static final String OAUTH2_COOKIE_NAME = "OAUTH2_AUTHORIZATION_REQUEST";
    public static final Duration OAUTH_COOKIE_EXPIRY = Duration.ofMinutes(5);

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return getCookie(request);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                         HttpServletResponse response) {
        if (isNull(authorizationRequest)) {
            removeAuthorizationRequest(request, response);
            return;
        }

        try {
            byte[] bytes = SerializationUtils.serialize(authorizationRequest);
            String encryptedText = Base64.getEncoder().encodeToString(bytes);
            CookieUtil.addCookie(response, OAUTH2_COOKIE_NAME + "1", encryptedText.substring(0, encryptedText.length() / 4), OAUTH_COOKIE_EXPIRY);
            CookieUtil.addCookie(response, OAUTH2_COOKIE_NAME + "2", encryptedText.substring(encryptedText.length() / 4, encryptedText.length() / 2), OAUTH_COOKIE_EXPIRY);
            CookieUtil.addCookie(response, OAUTH2_COOKIE_NAME + "3", encryptedText.substring(encryptedText.length() / 2, encryptedText.length() / 4 * 3), OAUTH_COOKIE_EXPIRY);
            CookieUtil.addCookie(response, OAUTH2_COOKIE_NAME + "4", encryptedText.substring(encryptedText.length() / 4 * 3), OAUTH_COOKIE_EXPIRY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = getCookie(request);
        CookieUtil.removeCookie(request, response, OAUTH2_COOKIE_NAME + "1");
        CookieUtil.removeCookie(request, response, OAUTH2_COOKIE_NAME + "2");
        CookieUtil.removeCookie(request, response, OAUTH2_COOKIE_NAME + "3");
        CookieUtil.removeCookie(request, response, OAUTH2_COOKIE_NAME + "4");
        return oAuth2AuthorizationRequest;
    }

    @SneakyThrows
    private OAuth2AuthorizationRequest getCookie(HttpServletRequest request) {
        Cookie cookie1 = CookieUtil.getCookie(request, OAUTH2_COOKIE_NAME + "1").orElse(null);
        Cookie cookie2 = CookieUtil.getCookie(request, OAUTH2_COOKIE_NAME + "2").orElse(null);
        Cookie cookie3 = CookieUtil.getCookie(request, OAUTH2_COOKIE_NAME + "3").orElse(null);
        Cookie cookie4 = CookieUtil.getCookie(request, OAUTH2_COOKIE_NAME + "4").orElse(null);
        String encryptedText = cookie1.getValue() + cookie2.getValue() + cookie3.getValue() + cookie4.getValue();
        return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(Base64.getDecoder().decode(encryptedText));
    }

    private String encrypt(OAuth2AuthorizationRequest authorizationRequest) throws Exception {
        byte[] bytes = SerializationUtils.serialize(authorizationRequest);
        return Aes256.encrypt(Arrays.toString(bytes));
    }

    private OAuth2AuthorizationRequest decrypt(String encryptedText) {
        byte[] bytes = null;
        try {
            bytes = Aes256.decrypt(Arrays.toString(encryptedText.getBytes(StandardCharsets.UTF_8))).getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(
                bytes);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class CookieUtil {

        public static Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
            return Optional.ofNullable(request.getCookies())
                    .flatMap(cookies -> Arrays.stream(cookies)
                            .filter(cookie -> cookie.getName().equals(cookieName))
                            .findFirst());
        }

        public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue,
                                     Duration maxAge) {
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setPath("/");
//            cookie.setDomain("localhost");
            cookie.setMaxAge((int) maxAge.toSeconds());

            response.addCookie(cookie);
        }

        public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
            Optional.of(request.getCookies())
                    .ifPresent(cookies ->
                            Arrays.stream(cookies)
                                    .filter(cookie -> cookie.getName().equals(cookieName))
                                    .forEach(cookie -> {
                                        cookie.setValue(EMPTY);
                                        cookie.setPath("/");
                                        cookie.setMaxAge(0);
                                        response.addCookie(cookie);
                                    })
                    );
        }
    }

    private static final class Aes256 {
        public static String alg = "AES/CBC/PKCS5Padding";
        private static final String key = "12345678910111213";
        private static final String iv = key.substring(0, 16); // 16byte

        public static String encrypt(String text) throws Exception {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        }

        public static String decrypt(String cipherText) throws Exception {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, "UTF-8");
        }
    }
}
