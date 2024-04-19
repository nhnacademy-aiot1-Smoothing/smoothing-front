package live.smoothing.front.notification.repository;

import live.smoothing.front.notification.dto.NotificationMessage;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis에서 Fcm Token을 관리하는 클래스
 *
 * @author 하지현
 */
public interface NotificationRepository {

    void saveToken(String userId, String fcmToken);

    void deleteToken(String userId, String fcmToken);

    Set<String> findAllTokensByUserId(String userId);

    void saveMessage(String userId, Map<String, String> message);

    List<NotificationMessage> getAllNotifications(String userId);

    boolean deleteNotification(String userId, String hashKey);
}
