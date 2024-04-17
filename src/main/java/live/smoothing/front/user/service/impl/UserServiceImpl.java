package live.smoothing.front.user.service.impl;

import live.smoothing.front.adapter.UserAdapter;
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

    private final UserAdapter userAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUser(UserCreateRequest userCreateRequest) {

        userAdapter.createUser(userCreateRequest);
    }
}
