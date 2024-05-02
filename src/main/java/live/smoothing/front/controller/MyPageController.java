package live.smoothing.front.controller;

import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
