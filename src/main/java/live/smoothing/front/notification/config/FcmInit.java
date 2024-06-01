package live.smoothing.front.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FcmInit {

    @Value("${fcm.credentials-file-path}")
    private String credentialsPath;

    @PostConstruct
    public void initialize() {

        try(InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream(credentialsPath)) {

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            } else {
                FirebaseApp.getInstance();
            }

        } catch(IOException e) {
            System.err.println("Failed to initialize FirebaseApp: " + e.getMessage());
            throw new RuntimeException("fFailed to initialize FirebaseApp", e);
        }
    }
}
