package live.smoothing.front.firebase.service;

import live.smoothing.front.firebase.repository.FcmTokenRepository;
import live.smoothing.front.firebase.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FcmTokenServiceImpl implements FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;

    @Override
    public boolean saveToken(String fcmToken) {

        fcmTokenRepository.save("user1", fcmToken);
        return true;
    }

    @Override
    public void deleteToken(String fcmToken) {

        fcmTokenRepository.delete("user1", fcmToken);
    }

    @Override
    public Set<String> getTokens(String userId) {

        return fcmTokenRepository.findAllTokensByUserId(userId);
    }
}
