package live.smoothing.front.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FcmNotificationService {

    public String sendDataMessage(String token, Map<String, String> data) throws FirebaseMessagingException {

        Message message = Message.builder()
                .putAllData(data)
                .setToken(token)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        return response;
    }
}
