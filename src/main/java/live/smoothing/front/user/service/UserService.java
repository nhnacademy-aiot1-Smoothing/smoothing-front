package live.smoothing.front.user.service;

import live.smoothing.front.user.dto.UserCreateRequest;
import live.smoothing.front.user.dto.UserProfileResponse;

/**
 * 유저 관련 서비스 인터페이스
 *
 * @author 박영준
 */
public interface UserService {

    /**
     * 유저 생성을 담당하는 메서드
     *
     * @param userCreateRequest 유저 생성 정보를 담은 DTO
     */
    void createUser(UserCreateRequest userCreateRequest);
}
