package live.smoothing.front.controller;

import live.smoothing.front.adapter.UserApiAdapter;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MypageController {

    private final UserApiAdapter userApiAdapter;

    public MypageController(UserApiAdapter userApiAdapter) {

        this.userApiAdapter = userApiAdapter;
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {

        Long pointBalance = userApiAdapter.getPointBalanceByUserId();
        model.addAttribute("pointBalance", pointBalance);

        return "pages/mypage";
    }

    @GetMapping("/point-details")
    public String pointDetail(Model model) {

        List <UserPointDetailResponse> pointDetails = userApiAdapter.getPointDetailsByUserId();
        model.addAttribute("pointDetails", pointDetails);

        return "pages/point";
    }
}
