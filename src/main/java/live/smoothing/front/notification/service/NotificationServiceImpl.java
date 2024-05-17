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
    public boolean saveToken(String userId, String fcmToken) {

        notificationRepository.saveToken(userId, fcmToken);
        return true;
    }

    @Override
    public void deleteToken(String userId, String fcmToken) {

        notificationRepository.deleteToken(userId, fcmToken);
    }

    @Override
    public Set<String> getTokens(String userId) {

        return notificationRepository.findAllTokensByUserId(userId);
    }

    @Override
    public boolean saveMessage(String userId, Map<String, String> message) {

        notificationRepository.saveMessage(userId, message);
        return true;
    }

    @Override
    public List<NotificationMessage> getAllNotifications(String userId) {

        return notificationRepository.getAllNotifications(userId);
    }

    @Override
    public boolean deleteMessage(String userId, String hashKey) {

        return notificationRepository.deleteNotification(userId, hashKey);
    }
}
