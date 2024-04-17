package live.smoothing.front.firebase.controller;

import live.smoothing.front.firebase.dto.FcmTokenRequest;
import live.smoothing.front.firebase.service.FcmTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FcmTokenController {

    @Autowired
    private FcmTokenService fcmTokenService;

    @PostMapping("/api/tokens")
    public ResponseEntity<?> saveToken(@RequestBody FcmTokenRequest request) {

        if(request.getFcmToken() == null) {
            return ResponseEntity.badRequest().body("Token is missing");
        }

        fcmTokenService.saveToken(request.getFcmToken());
        return ResponseEntity.ok().body("Token saved successfully");
    }

    @DeleteMapping("/api/tokens")
    public ResponseEntity<?> deleteToken(@RequestBody FcmTokenRequest request) {

        if(request.getFcmToken() == null) {
            return ResponseEntity.badRequest().body("Token is missing");
        }

        fcmTokenService.deleteToken(request.getFcmToken());
        return ResponseEntity.ok().body("Token deleted successfully");
    }
}
