package live.smoothing.front.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleResponse {

    private String roleInfo;

    public RoleResponse(String roleInfo) {

        this.roleInfo = roleInfo;
    }
}
