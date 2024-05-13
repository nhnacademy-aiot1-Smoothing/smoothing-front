package live.smoothing.front.user.dto;

import live.smoothing.front.user.dto.response.UserInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoListResponse {
    private List<UserInfoResponse> users;
    private int totalPage;
}
