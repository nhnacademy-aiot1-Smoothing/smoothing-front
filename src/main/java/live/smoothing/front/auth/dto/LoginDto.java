package live.smoothing.front.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LoginDto {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("userPassword")
    private String userPassword;

}
