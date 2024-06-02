package live.smoothing.front.user.service;

import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.user.dto.UserInfoListResponse;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.user.dto.WaitingUserListResponse;
import live.smoothing.front.user.dto.request.UserApproveRequest;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.dto.request.UserRoleModifyRequest;
import live.smoothing.front.user.dto.request.*;
import live.smoothing.front.user.dto.response.*;
import org.springframework.data.domain.Pageable;

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

    WaitingUserListResponse getWaitingUserUserList(Pageable pageable);

    MessageResponse approveUser(UserApproveRequest request);

    MessageResponse rejectUser(String userId);

    List<RoleResponse> getAllRoles();

    String getUserName();

    Long getPointBalanceByUserId();

    List<UserPointDetailResponse> getPointDetailsByUserId();

    MessageResponse verifyPwd(VerifyPwdRequest request);

    UserProfileResponse getProfile();

    MessageResponse modifyProfile(ModifyProfile request);

    MessageResponse modifyPwd(ModifyPwdRequest request);

    UserInfoListResponse getUserList(Pageable pageable);

    List<RoleResponse> getUserRoleList(String userId);

    MessageResponse deleteUser(String userId);

    MessageResponse modifyUserRole(UserRoleModifyRequest request);

    MessageResponse inactiveUser();

    List<HookTypeResponse> getHookTypes();

    void createUserHook(HookCreateRequest request);

    UserHookResponse getUserHook();

    void modifyUserHook(HookModifyRequest request);

    void deleteUserHook();

    MessageResponse existUser(String userId);

    void modifyUserName(UserNameModifyRequest request);

    void modifyUserEmail(UserEmailModifyRequest request);
}
