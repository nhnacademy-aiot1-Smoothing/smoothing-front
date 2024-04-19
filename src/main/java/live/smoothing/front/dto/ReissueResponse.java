package live.smoothing.front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JWT 토큰 응답 DTO
 *
 * @author 박영준
 */
@Getter
@AllArgsConstructor
public class ReissueResponse {

    private String accessToken;
    private String tokenType;
}