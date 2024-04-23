package live.smoothing.front.auth.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JWT 토큰 재발급 요청 DTO
 *
 * @author 박영준
 */
@Getter
@AllArgsConstructor
public class RefreshTokenRequest {

    private String refreshToken;
}
