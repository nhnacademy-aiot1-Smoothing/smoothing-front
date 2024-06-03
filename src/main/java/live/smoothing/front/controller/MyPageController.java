package live.smoothing.front.controller;

import live.smoothing.front.auth.dto.email.MessageResponse;
import live.smoothing.front.auth.dto.email.VerificationRequest;
import live.smoothing.front.user.dto.UserPointDetailResponse;
import live.smoothing.front.user.dto.request.*;
import live.smoothing.front.user.dto.response.HookTypeResponse;
import live.smoothing.front.user.dto.response.UserHookResponse;
import live.smoothing.front.user.dto.response.UserProfileResponse;
import live.smoothing.front.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MyPageController {

    private static final Logger log = LoggerFactory.getLogger(MyPageController.class);
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
    public String pointDetailPage(Model model) {

        List<UserPointDetailResponse> pointDetails = userService.getPointDetailsByUserId();
        model.addAttribute("pointDetails", pointDetails);

        return "pages/point";
    }

    @GetMapping("/achievement")
    public String achievementPage() {

        return "pages/achievement";
    }

    @GetMapping("/verify-pwd")
    public String verifyPwdPage(Model model) {

        UserProfileResponse response = userService.getProfile();
        model.addAttribute("userId", response.getUserId());

        return "pages/verify_password";
    }

    @ResponseBody
    @PostMapping("/verify-pwd")
    public void verifyPwd(@RequestBody VerifyPwdRequest request) {

        userService.verifyPwd(request);
    }

    @GetMapping("/user-modify")
    public String userModifyPage(Model model) {

        UserProfileResponse response = userService.getProfile();

        model.addAttribute("userId", response.getUserId());
        model.addAttribute("userName", response.getUserName());

        String userEmail = response.getUserEmail();
        String[] emailParts = userEmail.split("@");

        model.addAttribute("email", emailParts[0]);
        model.addAttribute("domain", emailParts[1]);

        model.addAttribute("userEmail", userEmail);

        List<HookTypeResponse> hookTypes = userService.getHookTypes();
        model.addAttribute("hookTypes", hookTypes);

        UserHookResponse userHook = userService.getUserHook();
        model.addAttribute("userHook", userHook);

        return "pages/user_modify";
    }

    @ResponseBody
    @PutMapping("/modify-pwd")
    public void modifyPwd(@RequestBody ModifyPwdRequest request) {

        userService.modifyPwd(request);
    }

    @ResponseBody
    @PutMapping("/modify-name")
    public void modifyName(@RequestBody UserNameModifyRequest request) {

        userService.modifyUserName(request);
    }

    @ResponseBody
    @PutMapping("/modify-email")
    public void modifyEmail(@RequestBody UserEmailModifyRequest request) {

        userService.modifyUserEmail(request);
    }


//    @PostMapping("/user-modify")
//    public String userModify(@RequestParam("userEmail") String userEmail, @RequestParam("userName") String userName, @RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword) {
//
////        MessageResponse response = userService.verifyPwd(new VerifyPwdRequest(currentPassword));
//
////        if(response.getMessage().equals("비밀번호 확인 완료")) {
//            log.info("userEmail:{}", userEmail);
//            userService.modifyProfile(new ModifyProfile(userName, userEmail));
//
//            if(newPassword != null && !newPassword.isEmpty()) {
//                // 새 비밀번호가 입력되었다면 비밀번호 수정
//                userService.modifyPwd(new ModifyPwdRequest(newPassword));
//            }
////        }
//
//        return "redirect:/mypage";
//    }

    @PostMapping("/inactiveUser")
    public String inactiveUser() {

        userService.inactiveUser();

        return "redirect:/login";
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

    @GetMapping("/webhook")
    public String webhookPage(Model model) {

        return "pages/webhook";
    }

    @GetMapping("/webhook-detail")
    public String webhookDetailPage(Model model) {

        return "pages/webhook_detail";
    }
}
