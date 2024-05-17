package live.smoothing.front.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class WaitingUserListResponse {
    private List<WaitingUser> waitingUsers;
    private int totalPage;
}
