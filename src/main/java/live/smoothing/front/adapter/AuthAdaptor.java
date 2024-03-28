package live.smoothing.front.adapter;

import live.smoothing.front.auth.dto.LoginRequest;
import live.smoothing.front.auth.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gateway")
public interface AuthAdaptor {

    @PostMapping("/login")
    ResponseEntity<LoginResponse> doLogin(@RequestBody LoginRequest loginRequest);
}
