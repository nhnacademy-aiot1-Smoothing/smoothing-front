package live.smoothing.front.adapter;

import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.user.dto.UserInfoListResponse;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.user.dto.WaitingUserListResponse;
import live.smoothing.front.user.dto.request.*;
import live.smoothing.front.user.dto.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/api/user/point/balance")
    Long getPointBalanceByUserId();

    @GetMapping("/api/user/point")
    List<UserPointDetailResponse> getPointDetailsByUserId();

    @GetMapping(value = "/api/user/profile/name")
    String getUserName();

    @GetMapping("/api/user/attendance/list/{year}/{month}")
    UserAttendanceResponse getAttendanceList(@PathVariable("year") int year,
                                             @PathVariable("month") int month);

    @PostMapping("/api/user/attendance")
    MessageResponse doAttendanceCheck();

    @GetMapping("/api/user/waitingUserList")
    WaitingUserListResponse getWaitingUserList(@RequestParam("page") int page,
                                               @RequestParam("size") int size);

    @PutMapping("/api/user/approve")
    MessageResponse approveUser(@RequestBody UserApproveRequest request);

    @DeleteMapping("/api/user/reject/{userId}")
    MessageResponse rejectUser(@PathVariable("userId") String userId);

    @GetMapping("/api/user/role/list")
    List<RoleResponse> getAllRoles();

    @GetMapping("/api/user/userList")
    UserInfoListResponse getUserList(@RequestParam("page") int page,
                                     @RequestParam("size") int size);

    @GetMapping("/api/user/userRole/list")
    List<RoleResponse> getUserRoleList(@RequestParam("userId") String userId);

    @DeleteMapping("/api/user/delete/{userId}")
    MessageResponse deleteUser(@PathVariable("userId") String userId);

    @PutMapping("/api/user/userRole")
    MessageResponse modifyUserRole(@RequestBody UserRoleModifyRequest request);

    @PostMapping("/api/user/password")
    MessageResponse verifyPwd(@RequestBody VerifyPwdRequest request);

    @GetMapping("/api/user/profile/modify")
    UserProfileResponse getProfile();

    @PutMapping("/api/user/profile")
    MessageResponse modifyUser(@RequestBody ModifyProfile request);

    @PutMapping("/api/user/profile/password")
    MessageResponse modifyPwd(@RequestBody ModifyPwdRequest request);

    @DeleteMapping("/api/user/inactive")
    MessageResponse inactiveUser();

    @GetMapping("/api/user/hookType/list")
    List<HookTypeResponse> getHookTypes();

    @PostMapping("/api/user/hook")
    void createUserHook(@RequestBody HookCreateRequest request);

    @GetMapping("/api/user/hook")
    UserHookResponse getUserHook();

    @PutMapping("/api/user/hook")
    void modifyUserHook(@RequestBody HookModifyRequest request);

    @DeleteMapping("/api/user/hook")
    void deleteUserHook();

    @GetMapping("/api/user/existUser")
    MessageResponse existUser(@RequestParam("userId") String userId);

}