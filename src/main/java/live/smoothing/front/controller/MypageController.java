package live.smoothing.front.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.smoothing.front.adapter.UserAdapter;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MypageController {

    private final UserAdapter userAdapter;

    public MypageController(UserAdapter userAdapter) {

        this.userAdapter = userAdapter;
    }

    @GetMapping("/mypage")
    public String mypage(Model model, HttpServletRequest request) throws JsonProcessingException {

        String jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("smoothing_accessToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }
        String userId = JwtUtil.getUserId(jwtToken);

        Long pointBalance = userAdapter.getPointBalanceByUserId(userId);
        model.addAttribute("pointBalance", pointBalance);

        return "pages/mypage";
    }

    @GetMapping("/point-details")
    public String pointDetail(Model model, HttpServletRequest request) throws JsonProcessingException {
        String jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("smoothing_accessToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }
        String userId = JwtUtil.getUserId(jwtToken);
        List<UserPointDetailResponse> pointDetails = userAdapter.getPointDetailsByUserId(userId);
        model.addAttribute("pointDetails", pointDetails);

        Long pointBalance = userAdapter.getPointBalanceByUserId(userId);
        model.addAttribute("pointBalance", pointBalance);
        return "pages/point";
    }
}
