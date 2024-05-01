package live.smoothing.front.user.service;

import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.user.dto.WaitingUser;
import live.smoothing.front.user.dto.request.UserApproveRequest;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.dto.response.RoleResponse;
import live.smoothing.front.user.dto.response.UserAttendanceResponse;

import java.util.List;

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

    UserAttendanceResponse getUserAttendance(int year, int month);

    MessageResponse doAttendanceCheck();

    List<WaitingUser> getWaitingUserList();

    List<WaitingUser> getWaitingUserList(int page, int size);

    MessageResponse approveUser(UserApproveRequest request);

    MessageResponse rejectUser(String userId);

    List<RoleResponse> getAllRoles();
}
