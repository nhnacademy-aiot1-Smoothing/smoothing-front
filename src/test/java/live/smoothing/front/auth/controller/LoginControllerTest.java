package live.smoothing.front.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.smoothing.front.adapter.AuthAdaptor;
import live.smoothing.front.auth.dto.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthAdaptor authAdaptor;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        when(authAdaptor.doLogin(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

    @Test
    public void testLoginSuccess() throws Exception {

        LoginRequest req = new LoginRequest("testId", "testPw");

        mockMvc.perform(post("/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().is3xxRedirection());
    }
}