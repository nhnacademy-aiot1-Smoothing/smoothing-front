package live.smoothing.front.adapter;

import feign.Headers;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.dto.response.UserAttendanceResponse;
import live.smoothing.front.user.dto.response.UserCreateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 유저 관리 관련 FeignClient
 *
 * @author 박영준
 */
@FeignClient("gateway")
public interface UserApiAdapter {
    /**
     * 유저 생성 요청 처리 및 응답 반환 메서드
     *
     * @param createRequest 유저 생성에 필요한 정보를 담고 있는 객체
     * @return 결과 메시지를 담은 응답 객체
     */
    @PostMapping("/api/user/signup")
    UserCreateResponse createUser(UserCreateRequest createRequest);

    @GetMapping("/api/user/attendance/list")
    UserAttendanceResponse getAttendanceList(@RequestHeader(name = "X-USER-ID") String userId);
}