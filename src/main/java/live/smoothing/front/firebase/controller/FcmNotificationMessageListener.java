package live.smoothing.front.firebase.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import live.smoothing.front.dto.NotificationMessage;
import live.smoothing.front.firebase.service.FcmNotificationService;
import live.smoothing.front.firebase.service.FcmTokenService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class FcmNotificationMessageListener {

    private final FcmTokenService fcmTokenService;
    private final FcmNotificationService fcmNotificationService;

    public FcmNotificationMessageListener(FcmTokenService fcmTokenService, FcmNotificationService fcmNotificationService) {

        this.fcmTokenService = fcmTokenService;
        this.fcmNotificationService = fcmNotificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue-name}")
    public void sendNotification(@Payload NotificationMessage message) {

        String userId = "user1";
        String title = message.getTitle();
        String body = message.getBody();

        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("body", body);

        try {
            Set<String> tokens = fcmTokenService.getTokens(userId);
            for(String token : tokens) {
                fcmNotificationService.sendDataMessage(token, data);
            }
        } catch(FirebaseMessagingException e) {
            e.getMessage();
        }
    }
}