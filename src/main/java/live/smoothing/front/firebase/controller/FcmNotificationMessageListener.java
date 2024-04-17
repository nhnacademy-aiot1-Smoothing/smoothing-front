package live.smoothing.front.firebase.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.netflix.discovery.converters.Auto;
import live.smoothing.front.firebase.service.FcmNotificationService;
import live.smoothing.front.firebase.service.FcmTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/notifications")
public class FcmNotificationMessageListener {

    private final FcmTokenService fcmTokenService;
    private final FcmNotificationService fcmNotificationService;

    public FcmNotificationMessageListener(FcmTokenService fcmTokenService, FcmNotificationService fcmNotificationService) {

        this.fcmTokenService = fcmTokenService;
        this.fcmNotificationService = fcmNotificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestParam String userId,
            @RequestParam String title,
            @RequestParam String body) {

        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("body", body);

        try {
            Set<String> tokens = fcmTokenService.getTokens(userId);
            for (String token : tokens) {
                fcmNotificationService.sendDataMessage(token, data);
            }
            return ResponseEntity.ok("Sent message");
        } catch(FirebaseMessagingException e) {
            return ResponseEntity.badRequest().body("Failed: " + e.getMessage());
        }
    }
}