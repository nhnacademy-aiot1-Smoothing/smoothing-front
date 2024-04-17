package live.smoothing.front.firebase.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FcmTokenRepositoryImpl implements FcmTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String userId, String fcmToken) {

        redisTemplate.opsForHash().put(userId, fcmToken, LocalDate.now().toString());
    }

    @Override
    public void delete(String userId, String fcmToken) {

        redisTemplate.opsForHash().delete("user1", "test2");
    }

    @Override
    public Set<String> findAllTokensByUserId(String userId) {

        return redisTemplate.opsForHash().keys(userId).stream()
                .map(key -> (String) key)
                .collect(Collectors.toSet());
    }
}
