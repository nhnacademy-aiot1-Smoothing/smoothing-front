package live.smoothing.front.adapter;

import feign.Headers;
import live.smoothing.front.auth.dto.email.EmailCertificationRequest;
import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.auth.dto.email.VerificationRequest;
import live.smoothing.front.auth.dto.login.LoginRequest;
import live.smoothing.front.auth.dto.login.LoginResponse;
import live.smoothing.front.auth.dto.token.RefreshTokenRequest;
import live.smoothing.front.auth.dto.token.ReissueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 인증 관련 FeignClient
 *
 * @author 박영준
 */
@FeignClient("gateway")
@Headers("Accept: application/json")
public interface AuthAdapter {

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

    /**
     * 로그아웃 요청 메서드
     *
     * @param tokenRequest 삭제할 refreshToken 을 담은 요청 객체
     * @return 없음
     *
     * @author 우혜승
     */
    @DeleteMapping("/api/auth/logout")
    ResponseEntity<Void> logout(@RequestBody RefreshTokenRequest tokenRequest);

    /**
     * 이메일 인증번호 요청 메서드
     *
     * @param emailCertificationRequest 사용자의 이메일 인증 요청 객체
     * @return 인증번호
     *
     * @author 김지윤
     */
    @PostMapping("/api/auth/email")
    ResponseEntity<MessageResponse> requestCertificationNumber(@RequestBody EmailCertificationRequest emailCertificationRequest);

    /**
     * 인증번호 확인 요청 메서드
     *
     * @param verificationRequest 인증번호 확인 요청 객체
     * @return 인증완료되면 true 실패하면 false
     *
     * @author 김지윤
     */

    @PostMapping("/api/auth/email/verify")
    ResponseEntity<MessageResponse> verifyCertificationNumber(@RequestBody VerificationRequest verificationRequest);

    @PostMapping("/api/auth/oauth")
    ResponseEntity<LoginResponse> doOAuthLogin(@RequestParam("userId") String userId);
}
