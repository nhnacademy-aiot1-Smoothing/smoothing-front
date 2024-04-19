package live.smoothing.front.notification.service;

import live.smoothing.front.notification.dto.NotificationMessage;
import live.smoothing.front.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public boolean saveToken(String fcmToken) {

        notificationRepository.saveToken("user1", fcmToken);
        return true;
    }

    @Override
    public void deleteToken(String fcmToken) {

        notificationRepository.deleteToken("user1", fcmToken);
    }

    @Override
    public Set<String> getTokens(String userId) {

        return notificationRepository.findAllTokensByUserId(userId);
    }

    @Override
    public boolean saveMessage(Map<String, String> message) {
        notificationRepository.saveMessage("user1", message);
        return true;
    }

    @Override
    public List<NotificationMessage> getAllNotifications(String userId) {

        return notificationRepository.getAllNotifications("user1");
    }

    @Override
    public boolean deleteMessage(String userId, String hashKey) {
        return notificationRepository.deleteNotification(userId, hashKey);
    }
}
