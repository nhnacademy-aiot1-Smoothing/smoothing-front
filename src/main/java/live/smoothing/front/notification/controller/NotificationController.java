package live.smoothing.front.notification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.notification.dto.NotificationMessage;
import live.smoothing.front.notification.service.NotificationService;
import live.smoothing.front.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public List<NotificationMessage> getNotifications(HttpServletRequest request) throws JsonProcessingException {
        String jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("smoothing_accessToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        String userId = JwtUtil.getUserId(jwtToken);
        return notificationService.getAllNotifications(userId);
    }

    @DeleteMapping("/notifications/{hashKey}")
    public ResponseEntity<?> deleteNotification(@PathVariable("hashKey") String hashKey, HttpServletRequest request) throws JsonProcessingException {
        String jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("smoothing_accessToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        String userId = JwtUtil.getUserId(jwtToken);

        boolean deleted = notificationService.deleteMessage(userId, hashKey);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
