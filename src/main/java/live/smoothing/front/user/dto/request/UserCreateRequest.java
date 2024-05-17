package live.smoothing.front.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저 생성 요청 DTO
 *
 * @author 박영준
 */
@Getter
@AllArgsConstructor
public class UserCreateRequest {

    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
}
