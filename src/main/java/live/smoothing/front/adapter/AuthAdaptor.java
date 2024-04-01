package live.smoothing.front.adapter;

import live.smoothing.front.auth.dto.LoginRequest;
import live.smoothing.front.auth.dto.LoginResponse;
import live.smoothing.front.dto.RefreshTokenRequest;
import live.smoothing.front.dto.ReissueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 유저 인증 관련 FeignClient
 *
 * @author 박영준
 */
@FeignClient("gateway")
public interface AuthAdaptor {

    /**
     * 로그인 요청 처리 및 로그인 응답 반환 메서드
     *
     * @param loginRequest 유저 아이디와 비밀번호를 담은 로그인 요청 객체
     * @return accessToken, refreshToken, tokenType을 담은 로그인 응답 객체
     */
    @PostMapping("/api/auth/login")
    ResponseEntity<LoginResponse> doLogin(@RequestBody LoginRequest loginRequest);

    /**
     * accessToken 재발급 요청 및 응답 반환 메서드
     *
     * @param tokenRequest refreshToken을 담은 재발급 요청 객체
     * @return accessToken, tokenType을 담은 재발급 응답 객체
     */
    @PostMapping("/api/auth/refresh")
    ResponseEntity<ReissueResponse> refreshToken(@RequestBody RefreshTokenRequest tokenRequest);
}
