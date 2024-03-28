package live.smoothing.front.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("userPassword")
    private String userPassword;

    public LoginRequest(String testId, String testPw) {
        this.userId = testId;
        this.userPassword = testPw;
    }
}
