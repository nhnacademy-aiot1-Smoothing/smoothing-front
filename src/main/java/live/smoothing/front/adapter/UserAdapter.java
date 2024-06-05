package live.smoothing.front.adapter;

import live.smoothing.front.user.dto.UserIdDto;
import live.smoothing.front.user.dto.UserOAuthExistDto;
import live.smoothing.front.user.dto.UserOAuthListDto;
import live.smoothing.front.user.dto.response.UserIdListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@FeignClient("user-service")
public interface UserAdapter {

    @GetMapping("/api/user/userRole/list/roleId/{roleId}")
    UserIdListResponse getUserIds(@PathVariable("roleId") Long roleId);


    @GetMapping("/api/user/oauth/exist")
    UserOAuthExistDto getUserOAuthByProviderKey_providerName(@RequestParam("providerKey") String providerKey,
                                                             @RequestParam("providerName") String providerName);

    @PostMapping("/api/user/oauth")
    void saveUserOAuth(@RequestParam("providerKey") String providerKey,
                       @RequestParam("providerId") String providerId,
                       @RequestHeader("X-USER-ID") String userId);

    @GetMapping("/api/user/oauth/userId")
    UserIdDto getUserIdByProviderKey_providerName(@RequestParam("providerKey") String providerKey,
                                                 @RequestParam("providerName") String providerName);

}