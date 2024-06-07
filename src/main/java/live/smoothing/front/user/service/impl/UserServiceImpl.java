package live.smoothing.front.user.service.impl;


import feign.FeignException;
import live.smoothing.front.adapter.UserAdapter;
import live.smoothing.front.adapter.UserApiAdapter;
import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.user.dto.UserInfoListResponse;
import live.smoothing.front.user.dto.UserOAuthListDto;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.user.dto.WaitingUserListResponse;
import live.smoothing.front.user.dto.request.UserApproveRequest;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.dto.request.UserRoleModifyRequest;
import live.smoothing.front.user.dto.request.*;
import live.smoothing.front.user.dto.response.*;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * 유저 서비스 구현체
 *
 * @author 박영준
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserApiAdapter userApiAdapter;
    private final UserAdapter userAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUser(UserCreateRequest userCreateRequest) {

        userApiAdapter.createUser(userCreateRequest);
    }

    @Override
    public UserAttendanceResponse getUserAttendance(int year, int month) {

        return userApiAdapter.getAttendanceList(year, month);
    }

    @Override
    public MessageResponse doAttendanceCheck() {

        return userApiAdapter.doAttendanceCheck();
    }

    @Override
    public WaitingUserListResponse getWaitingUserUserList(Pageable pageable) {
        return userApiAdapter.getWaitingUserList(pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public MessageResponse approveUser(UserApproveRequest request) {

        return userApiAdapter.approveUser(request);
    }

    @Override
    public MessageResponse rejectUser(String userId) {

        return userApiAdapter.rejectUser(userId);
    }

    @Override
    public List<RoleResponse> getAllRoles() {

        return userApiAdapter.getAllRoles();
    }

    @Override
    public String getUserName() {

        return userApiAdapter.getUserName();
    }

    @Override
    public Long getPointBalanceByUserId() {

        return userApiAdapter.getPointBalanceByUserId();
    }

    @Override
    public List<UserPointDetailResponse> getPointDetailsByUserId() {

        return userApiAdapter.getPointDetailsByUserId();
    }

    @Override
    public MessageResponse verifyPwd(VerifyPwdRequest request) {

        try {
            return userApiAdapter.verifyPwd(request);
        } catch(FeignException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Override
    public UserProfileResponse getProfile() {

        return userApiAdapter.getProfile();
    }

    @Override
    public MessageResponse modifyProfile(ModifyProfile request) {

        return userApiAdapter.modifyUser(request);
    }

    @Override
    public MessageResponse modifyPwd(ModifyPwdRequest request) {

        return userApiAdapter.modifyPwd(request);
    }

    @Override
    public UserInfoListResponse getUserList(Pageable pageable) {

        return userApiAdapter.getUserList(pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public List<RoleResponse> getUserRoleList(String userId) {

        return userApiAdapter.getUserRoleList(userId);
    }

    @Override
    public MessageResponse deleteUser(String userId) {

        return userApiAdapter.deleteUser(userId);
    }

    @Override
    public MessageResponse modifyUserRole(UserRoleModifyRequest request) {

        return userApiAdapter.modifyUserRole(request);
    }

    @Override
    public MessageResponse inactiveUser() {

        return userApiAdapter.inactiveUser();
    }

    @Override
    public List<HookTypeResponse> getHookTypes() {

        return userApiAdapter.getHookTypes();
    }

    @Override
    public void createUserHook(HookCreateRequest request) {

        userApiAdapter.createUserHook(request);
    }

    @Override
    public UserHookResponse getUserHook() {

        return userApiAdapter.getUserHook();
    }

    @Override
    public void modifyUserHook(HookModifyRequest request) {

        userApiAdapter.modifyUserHook(request);
    }

    @Override
    public void deleteUserHook() {

        userApiAdapter.deleteUserHook();
    }

    @Override
    public MessageResponse existUser(String userId) {

        try {
            return userApiAdapter.existUser(userId);
        } catch(FeignException.Conflict e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public void modifyUserName(UserNameModifyRequest request) {

        userApiAdapter.modifyUserName(request);
    }

    @Override
    public void modifyUserEmail(UserEmailModifyRequest request) {

        userApiAdapter.modifyUserEmail(request);
    }

    @Override
    public UserOAuthListDto getUserOAuthList() {
        return userApiAdapter.getUserOAuthList();
    }

    @Override
    public boolean getUserOAuthByProviderKey_providerName(String providerKey, String providerName) {
        return userAdapter.getUserOAuthByProviderKey_providerName(providerKey, providerName).isExist();
    }

    @Override
    public void saveUserOAuth(String providerKey, String providerId, String userId) {
        userAdapter.saveUserOAuth(providerKey, providerId, userId);
    }

    @Override
    public String getUserIdByProviderKey_providerName(String providerKey, String providerName) {
        return userAdapter.getUserIdByProviderKey_providerName(providerKey, providerName).getUserId();
    }

    @Override
    public void deleteUserOAuth(String providerName) {
        userApiAdapter.deleteUserOAuth(providerName);
    }


}
