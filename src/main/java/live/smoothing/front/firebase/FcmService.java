package live.smoothing.front.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {
    public String sendNotification(String token, String title, String body) throws FirebaseMessagingException {

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(token)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        return response;
    }
}
