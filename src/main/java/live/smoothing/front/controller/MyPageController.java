package live.smoothing.front.controller;

import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.user.dto.request.*;
import live.smoothing.front.user.dto.response.HookTypeResponse;
import live.smoothing.front.user.dto.response.UserHookResponse;
import live.smoothing.front.user.dto.response.UserProfileResponse;
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

        return "redirect:/user-modify";
    }

    @GetMapping("/user-modify")
    public String UserModifyPage(Model model) {

        UserProfileResponse response = userService.getProfile();

        model.addAttribute("userId", response.getUserId());
        model.addAttribute("userName", response.getUserName());
        model.addAttribute("userEmail", response.getUserEmail());

        List<HookTypeResponse> hookTypes = userService.getHookTypes();
        model.addAttribute("hookTypes", hookTypes);

        UserHookResponse userHook = userService.getUserHook();
        model.addAttribute("userHook", userHook);

        return "pages/user_modify";
    }

    @PostMapping("/user-modify")
    public String verifyPwd(@RequestParam("userEmail") String userEmail, @RequestParam("userName") String userName, @RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword) {

        MessageResponse response = userService.verifyPwd(new VerifyPwdRequest(currentPassword));

        if(response.getMessage().equals("비밀번호 확인 완료")) {

            userService. modifyProfile(new ModifyProfile(userName, userEmail));
            if(newPassword.length() > 0) {
                userService.modifyPwd(new ModifyPwdRequest(newPassword));
            }
        } else {
            return "redirect:/user-modify";
        }

        return "redirect:/mypage";
    }

    @ResponseBody
    @PostMapping("/createHook")
    public void createUserHook(@RequestBody HookCreateRequest request) {

        userService.createUserHook(request);
    }

    @ResponseBody
    @PutMapping("/modifyHook")
    public void modifyUserHook(@RequestBody HookModifyRequest request) {

        userService.modifyUserHook(request);
    }

    @ResponseBody
    @DeleteMapping("/deleteHook")
    public void deleteUserHook() {

        userService.deleteUserHook();
    }
}
