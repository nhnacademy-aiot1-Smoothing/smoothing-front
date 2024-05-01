package live.smoothing.front.user.service.impl;

import live.smoothing.front.adapter.UserApiAdapter;
import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.dto.response.UserCreateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserApiAdapter userApiAdapter;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("유저 생성시 어댑터의 함수를 지정된 횟수만큼 호출한다.")
    void userCreateSuccess() {
        UserCreateRequest request = new UserCreateRequest("userId", "userPassword", "userName", "userEmail");
        UserCreateResponse response = new UserCreateResponse();
        ReflectionTestUtils.setField(response, "message", "사용자 생성을 성공하였습니다.");

        when(userApiAdapter.createUser(any())).thenReturn(response);

        userService.createUser(request);

        verify(userApiAdapter, times(1)).createUser(any());
    }
}