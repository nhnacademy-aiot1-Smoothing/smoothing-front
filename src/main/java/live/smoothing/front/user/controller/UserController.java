package live.smoothing.front.user.controller;

import live.smoothing.front.user.dto.request.UserCreateRequest;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 유저 관련 컨트롤러
 *
 * @author 박영준
 */
@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 유저 생성
     *
     * @param userCreateRequest 유저 생성 정보를 담은 DTO
     * @return 사용자 생성이 성공하면 기본 페이지(/)로 redirection
     */
    @PostMapping
    public String createUser(@ModelAttribute UserCreateRequest userCreateRequest) {

        userService.createUser(userCreateRequest);
        return "redirect:/";
    }
}