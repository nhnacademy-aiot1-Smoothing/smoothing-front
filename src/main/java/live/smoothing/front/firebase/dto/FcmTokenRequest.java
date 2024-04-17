package live.smoothing.front.firebase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FcmTokenRequest {

    private String fcmToken;

    public FcmTokenRequest(String fcmToken) {

        this.fcmToken = fcmToken;
    }
}
