package live.smoothing.front.token.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 토큰과 토큰 타입을 담는 DTO
 * @see live.smoothing.front.util.CookieUtil 을 통해 쿠키에 저장할 때 사용
 *
 * @author 우혜승
 */
@Getter
@AllArgsConstructor
public class TokenWithType {

    private String tokenType;
    private String token;
}
