package live.smoothing.front.controller;

import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.user.dto.request.VerifyPwdRequest;
import live.smoothing.front.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MyPageController {

    private final UserService userService;

    public MyPageController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/mypage")
    public String myPage(Model model) {

        Long pointBalance = userService.getPointBalanceByUserId();
        String userName = userService.getUserName();

        model.addAttribute("pointBalance", pointBalance);
        model.addAttribute("userName", userName);

        return "pages/mypage";
    }

    @GetMapping("/point-details")
    public String pointDetail(Model model) {

        List<UserPointDetailResponse> pointDetails = userService.getPointDetailsByUserId();
        model.addAttribute("pointDetails", pointDetails);

        return "pages/point";
    }

    @GetMapping("/achievement")
    public String achievement() {

        return "pages/achievement";
    }

    @GetMapping("/verify-pwd")
    public String verifyPwdPage() {

        return "pages/verify_password";
    }

    @PostMapping("/verify-pwd")
    public String verifyPwd(@RequestParam("userPassword") String userPassword) {

        userService.verifyPwd(new VerifyPwdRequest(userPassword));

        return "pages/user_modify";
    }

    @GetMapping("/user-modify")
    public String UserModifyPage() {

        return "redirect:/verify-pwd";
    }
}
