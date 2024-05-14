package live.smoothing.front.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserHookResponse {

    private Integer hookTypeId;
    private String hookTypeName;
    private String hookUrl;
}
