package live.smoothing.front.adapter;

import live.smoothing.front.auth.dto.LoginRequest;
import live.smoothing.front.auth.dto.LoginResponse;
import live.smoothing.front.dto.RefreshTokenRequest;
import live.smoothing.front.dto.ReissueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gateway")
public interface AuthAdaptor {

    @PostMapping("/api/auth/login")
    ResponseEntity<LoginResponse> doLogin(@RequestBody LoginRequest loginRequest);

    @PostMapping("/api/auth/refresh")
    ResponseEntity<ReissueResponse> refreshToken(@RequestBody RefreshTokenRequest tokenRequest);
}
