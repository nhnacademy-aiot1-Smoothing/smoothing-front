package live.smoothing.front.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HookCreateRequest {

    private Integer hookTypeId;
    private String hookUrl;
}
