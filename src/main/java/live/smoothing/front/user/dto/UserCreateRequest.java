package live.smoothing.front.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequest {
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
}
