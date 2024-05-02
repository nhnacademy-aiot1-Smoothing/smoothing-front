package live.smoothing.front.user.service.impl;


import live.smoothing.front.adapter.UserApiAdapter;
import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.user.dto.WaitingUser;
import live.smoothing.front.user.dto.request.UserApproveRequest;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.dto.response.RoleResponse;
import live.smoothing.front.user.dto.response.UserAttendanceResponse;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public List<WaitingUser> getWaitingUserList() {

        return userApiAdapter.getWaitingUserList();
    }

    @Override
    public List<WaitingUser> getWaitingUserList(int page, int size) {

        return userApiAdapter.getWaitingUserList(page, size);
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
}
