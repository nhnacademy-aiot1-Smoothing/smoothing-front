package live.smoothing.front.token;

import live.smoothing.front.token.entity.TokenWithType;
import lombok.extern.slf4j.Slf4j;

/**
 * TokenWithType을 ThreadLocal에 가지는 클래스
 *
 * @author : 신민석
 */
@Slf4j
public class ThreadLocalToken {

    private ThreadLocalToken() {
        throw new IllegalStateException("Utility class");
    }

    public static final ThreadLocal<TokenWithType> TOKEN = new ThreadLocal<>();

}