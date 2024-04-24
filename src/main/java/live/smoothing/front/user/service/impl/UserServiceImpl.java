package live.smoothing.front.user.service.impl;


import live.smoothing.front.adapter.UserApiAdapter;
import live.smoothing.front.user.dto.UserCreateRequest;
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
}
