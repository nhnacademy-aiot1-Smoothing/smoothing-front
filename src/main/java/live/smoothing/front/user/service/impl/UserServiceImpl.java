package live.smoothing.front.user.service.impl;

import live.smoothing.front.adapter.UserAdapter;
import live.smoothing.front.user.dto.UserCreateRequest;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAdapter userAdapter;

    @Override
    public void createUser(UserCreateRequest userCreateRequest) {
        userAdapter.createUser(userCreateRequest);
    }
}
