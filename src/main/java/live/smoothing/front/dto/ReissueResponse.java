package live.smoothing.front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueResponse {
    private String accessToken;
    private String tokenType;
}