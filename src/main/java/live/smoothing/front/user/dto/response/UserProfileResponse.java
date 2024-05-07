package live.smoothing.front.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {

    private final String userName;
    private final String userEmail;
    private final String userId;
}
