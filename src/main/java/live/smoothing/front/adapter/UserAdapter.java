package live.smoothing.front.adapter;

import live.smoothing.front.user.dto.UserCreateRequest;
import live.smoothing.front.user.dto.UserCreateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("gateway1")
public interface UserAdapter {

    @PostMapping("/api/user")
    UserCreateResponse createUser(UserCreateRequest createRequest);
}