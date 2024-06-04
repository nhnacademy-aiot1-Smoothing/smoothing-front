package live.smoothing.front.controller;

import live.smoothing.front.user.dto.UserInfoListResponse;
import live.smoothing.front.user.dto.WaitingUser;
import live.smoothing.front.user.dto.WaitingUserListResponse;
import live.smoothing.front.user.dto.request.UserApproveRequest;
import live.smoothing.front.user.dto.request.UserRoleModifyRequest;
import live.smoothing.front.user.dto.response.RoleResponse;
import live.smoothing.front.user.dto.response.UserInfoResponse;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserManagementController {

    private final UserService userService;

    @GetMapping("/user-approval") // 회원 승인 페이지
    public String userApprovalPage(Model model,
                                   Pageable pageable) {

        List<RoleResponse> roleList = userService.getAllRoles();
        model.addAttribute("roleList", roleList);

        WaitingUserListResponse waitingUserListResponse = userService.getWaitingUserUserList(pageable);
        model.addAttribute("waitingUserList", waitingUserListResponse.getWaitingUsers());

        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", waitingUserListResponse.getTotalPage());

        return "pages/user_approval";
    }

    @PostMapping("/approve") // 회원 승인
    public String approveUser(@RequestBody UserApproveRequest request) {

        userService.approveUser(request);

        return "redirect:/user-approval";
    }

   @PostMapping("/reject") // 회원 승인 거절
   public String rejectUser(String userId) {

        userService.rejectUser(userId);

        log.info("회원 승인 거절 완료");
        return "redirect:/user-approval";
    }

    @GetMapping("/user-list") // 회원 목록 조회 페이지
    public String userList(Model model,
                           Pageable pageable) {

        List<RoleResponse> roleList = userService.getAllRoles();
        model.addAttribute("roleList", roleList);

        UserInfoListResponse userList = userService.getUserList(pageable);
        model.addAttribute("userList", userList.getUsers());

        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", userList.getTotalPage());


        return "pages/user_list";
    }

    @ResponseBody
    @GetMapping("/userRoleList")
    public List<RoleResponse> userRoleList(@RequestParam String userId) {

        return userService.getUserRoleList(userId);
    }

    @ResponseBody
    @GetMapping("/roleList")
    public List<RoleResponse> roleList() {

        return userService.getAllRoles();
    }

    @ResponseBody
    @DeleteMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable String userId) {

        userService.deleteUser(userId);

        return "redirect:/user-list";
    }

    @PostMapping("/modifyUserRole")
    public String modifyUserRole(@RequestBody UserRoleModifyRequest request) {

        userService.modifyUserRole(request);

        return "redirect:/user-list";
    }

}
