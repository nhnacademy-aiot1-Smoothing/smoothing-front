package live.smoothing.front.notification.controller;

import live.smoothing.front.notification.dto.NotificationMessage;
import live.smoothing.front.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public List<NotificationMessage> getNotifications() {
        return notificationService.getAllNotifications("user1");
    }

    @DeleteMapping("/notifications/{hashKey}")
    public ResponseEntity<?> deleteNotification(@PathVariable("hashKey") String hashKey) {
        boolean deleted = notificationService.deleteMessage("user1", hashKey);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
