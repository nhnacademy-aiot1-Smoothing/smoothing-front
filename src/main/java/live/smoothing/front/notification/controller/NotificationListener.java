package live.smoothing.front.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import live.smoothing.front.adapter.UserAdapter;
import live.smoothing.front.notification.dto.RabbitMqMessage;
import live.smoothing.front.notification.service.FcmNotificationService;
import live.smoothing.front.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class NotificationListener {

    private final NotificationService notificationService;
    private final FcmNotificationService fcmNotificationService;
    private final UserAdapter userAdapter;

    public NotificationListener(NotificationService notificationService, FcmNotificationService fcmNotificationService, UserAdapter userAdapter) {

        this.notificationService = notificationService;
        this.fcmNotificationService = fcmNotificationService;
        this.userAdapter = userAdapter;
    }

    @RabbitListener(queues = "${rabbitmq.queue-name}")
    public void receiveNotification(@Payload RabbitMqMessage message) {

        Map<String, String> data = new HashMap<>();
        data.put("title", message.getTitle() + "_" + LocalDateTime.now());
        data.put("body", message.getBody());

        List<Long> roleIds = new ArrayList<>();
        if (message.getType().equals("approval_request")) {
            roleIds.add(1L);
        } else if (message.getType().equals("outlier_detection")) {
            roleIds.add(1L);
            roleIds.add(2L);
        }

        for (Long roleId : roleIds) {
            List<String> userIds = userAdapter.getUserIds(roleId).getUserIds();

            for(String userId : userIds) {
                sendNotification(userId, message.getTitle(), message.getBody());
                saveNotification(userId, data);
            }
        }
    }

    public void sendNotification(String userId, String title, String body) {

        try {
            Set<String> tokens = notificationService.getTokens(userId);
            for(String token : tokens) {
                fcmNotificationService.sendNotification(token, title, body);
            }
        } catch(FirebaseMessagingException e) {
            System.out.println("Error sending notification: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveNotification(String userId, Map<String, String> data) {

        notificationService.saveMessage(userId, data);
    }
}