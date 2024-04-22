package live.smoothing.front.notification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.notification.dto.FcmTokenRequest;
import live.smoothing.front.notification.service.NotificationService;
import live.smoothing.front.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
public class FcmTokenController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/api/tokens")
    public ResponseEntity<?> saveToken(@RequestBody FcmTokenRequest fcmTokenRequest, HttpServletRequest request) throws JsonProcessingException {

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

        String fcmToken = fcmTokenRequest.getFcmToken();
        String userId = JwtUtil.getUserId(jwtToken);

        if(userId == null || fcmToken == null) {
            return ResponseEntity.badRequest().body("Token or userId is missing");
        }

        notificationService.saveToken(userId, fcmToken);
        return ResponseEntity.ok().body("Token saved successfully");
    }

    @DeleteMapping("/api/tokens")
    public ResponseEntity<?> deleteToken(HttpServletRequest request, @RequestBody FcmTokenRequest fcmTokenRequest) throws JsonProcessingException {

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

        String fcmToken = fcmTokenRequest.getFcmToken();
        String userId = JwtUtil.getUserId(jwtToken);

        if(userId == null || fcmToken == null) {
            return ResponseEntity.badRequest().body("Token or userId is missing");
        }

        notificationService.deleteToken(userId, fcmToken);
        return ResponseEntity.ok().body("Token deleted successfully");
    }
}
