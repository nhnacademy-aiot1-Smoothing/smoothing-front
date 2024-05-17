package live.smoothing.front.notification.service;

import live.smoothing.front.notification.dto.NotificationMessage;
import live.smoothing.front.notification.dto.RabbitMqMessage;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NotificationService {

    boolean saveToken(String userId, String fcmToken);

    void deleteToken(String userId, String fcmToken);

    Set<String> getTokens(String userId);

    boolean saveMessage(String userId, Map<String, String> message);

    List<NotificationMessage> getAllNotifications(String userId);

    boolean deleteMessage(String userId, String hashKey);
}
