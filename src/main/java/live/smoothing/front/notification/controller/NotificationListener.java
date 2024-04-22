package live.smoothing.front.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import live.smoothing.front.notification.dto.RabbitMqMessage;
import live.smoothing.front.notification.service.FcmNotificationService;
import live.smoothing.front.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class NotificationListener {

    private final NotificationService notificationService;
    private final FcmNotificationService fcmNotificationService;

    public NotificationListener(NotificationService notificationService, FcmNotificationService fcmNotificationService) {

        this.notificationService = notificationService;
        this.fcmNotificationService = fcmNotificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue-name}")
    public void receiveNotification(@Payload RabbitMqMessage message) {
        String title = message.getTitle() + "_" + LocalDateTime.now();
        String body = message.getBody();

        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("body", body);

        sendNotification(message.getTitle(), message.getBody());
        saveNotification(data);
    }

    public void sendNotification(String title, String body) {
        try {
            String userId = "hozzi";
            Set<String> tokens = notificationService.getTokens(userId);
            for(String token : tokens) {
                fcmNotificationService.sendNotification(token, title, body);
            }
        } catch(FirebaseMessagingException e) {
            System.out.println("Error sending notification: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveNotification(Map<String, String> data){
        String userId = "hozzi";
        notificationService.saveMessage(userId, data);
    }
}