package live.smoothing.front.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private String userId;
    private String userName;
    private String userEmail;
}
