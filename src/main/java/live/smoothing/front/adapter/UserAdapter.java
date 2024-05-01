package live.smoothing.front.adapter;

import live.smoothing.front.user.dto.response.UserIdListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("user-service")
public interface UserAdapter {

    @GetMapping("/api/user/userRole/list/roleId/{roleId}")
    UserIdListResponse getUserIds(@PathVariable("roleId") Long roleId);
}