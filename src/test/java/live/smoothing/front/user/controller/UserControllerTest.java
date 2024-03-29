package live.smoothing.front.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.smoothing.front.interceptor.ReissueJwtTokenInterceptor;
import live.smoothing.front.user.dto.UserCreateRequest;
import live.smoothing.front.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    ReissueJwtTokenInterceptor reissueJwtTokenInterceptor;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 생성 성공시 redirection 반환")
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    void successCreateUser() throws Exception {
        given(reissueJwtTokenInterceptor.preHandle(any(), any(), any())).willReturn(true);
        UserCreateRequest createRequest = new UserCreateRequest("userId", "userPassword", "userName", "userEmail");

        mockMvc.perform(post("/api/user").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().is3xxRedirection());
    }
}