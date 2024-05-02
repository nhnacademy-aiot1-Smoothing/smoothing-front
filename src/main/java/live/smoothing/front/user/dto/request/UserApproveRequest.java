package live.smoothing.front.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserApproveRequest {

    private String userId;
    private List<Long> roleIds;
}
