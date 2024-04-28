package live.smoothing.front.adapter;

import live.smoothing.front.user.dto.UserIdListResponse;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("user-service")
public interface UserAdapter {

    @GetMapping("/api/user/userRole/list/roleId/{roleId}")
    UserIdListResponse getUserIds(@PathVariable("roleId") Long roleId);

    @GetMapping("/api/user/point/balance")
    Long getPointBalanceByUserId(@RequestHeader("X-USER-ID") String userId);

    @GetMapping("/api/user/point")
    List<UserPointDetailResponse> getPointDetailsByUserId(@RequestHeader("X-USER-ID") String userId);
}