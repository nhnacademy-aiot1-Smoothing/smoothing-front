package live.smoothing.front.firebase.service;

import java.util.Set;

public interface FcmTokenService {

    boolean saveToken(String fcmToken);

    void deleteToken(String fcmToken);

    Set<String> getTokens(String userId);
}
