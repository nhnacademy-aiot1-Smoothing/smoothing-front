package live.smoothing.front.firebase.repository;

import java.util.Set;

/**
 * redis에서 Fcm Token을 관리하는 클래스
 *
 * @author 하지현
 */
public interface FcmTokenRepository {

    void save(String userId, String fcmToken);

    void delete(String userId, String fcmToken);

    Set<String> findAllTokensByUserId(String userId);
}
