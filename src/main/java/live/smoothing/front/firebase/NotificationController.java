package live.smoothing.front.firebase;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final FcmService fcmService;

    public NotificationController(FcmService fcmService) {

        this.fcmService = fcmService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestParam String token,
            @RequestParam String title,
            @RequestParam String body) {
        try {
            String response = fcmService.sendNotification(token, title, body);
            return ResponseEntity.ok("Sent message: " + response);
        } catch(FirebaseMessagingException e) {
            return ResponseEntity.badRequest().body("Failed: " + e.getMessage());
        }
    }
}
