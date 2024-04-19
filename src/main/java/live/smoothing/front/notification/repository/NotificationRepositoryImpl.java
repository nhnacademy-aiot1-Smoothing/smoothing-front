package live.smoothing.front.notification.repository;

import live.smoothing.front.notification.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveToken(String userId, String fcmToken) {

        redisTemplate.opsForHash().put(userId, fcmToken, LocalDate.now().toString());
    }

    @Override
    public void deleteToken(String userId, String fcmToken) {

        redisTemplate.opsForHash().delete("user1", fcmToken);
    }

    @Override
    public Set<String> findAllTokensByUserId(String userId) {

        return redisTemplate.opsForHash().keys(userId).stream()
                .map(key -> (String) key)
                .collect(Collectors.toSet());
    }

    @Override
    public void saveMessage(String userId, Map<String, String> message) {

        redisTemplate.opsForHash().put(userId + ":message", message.get("title"), message.get("body"));
    }

    @Override
    public List<NotificationMessage> getAllNotifications(String userId) {

        Map<Object, Object> rawMessages = redisTemplate.opsForHash().entries("user1:message");

        List<NotificationMessage> notifications = new ArrayList<>();
        rawMessages.forEach((key, value) -> {
            String title = key.toString().split("_")[0];
            String dateTimeString = key.toString().split("_")[1];

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
            notifications.add(new NotificationMessage("user1", title, (String) value, dateTime));
        });

        return notifications;
    }

    @Override
    public boolean deleteNotification(String userId, String hashKey) {

        return redisTemplate.opsForHash().delete(userId + ":message", hashKey) > 0;
    }
}
