package live.smoothing.front.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "/api/auth/login")
public interface LoginAdaptor {

    @GetMapping("/login")
    String loginForm();

    @PostMapping("/login")
    ResponseEntity<?> doLogin(@RequestBody String userId,
                              @RequestBody String userPassword);
}
