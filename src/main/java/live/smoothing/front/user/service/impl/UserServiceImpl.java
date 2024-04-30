package live.smoothing.front.user.service.impl;


import live.smoothing.front.adapter.UserApiAdapter;
import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.dto.response.UserAttendanceResponse;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
