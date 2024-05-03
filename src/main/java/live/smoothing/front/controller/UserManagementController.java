package live.smoothing.front.controller;

import live.smoothing.front.user.dto.WaitingUser;
import live.smoothing.front.user.dto.request.UserApproveRequest;
import live.smoothing.front.user.dto.response.RoleResponse;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserManagementController {

    private final UserService userService;

    @GetMapping("/user-approval")
    public String userApprovalPage(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "7") int size) {

        List<RoleResponse> roleList = userService.getAllRoles();
        model.addAttribute("roleList", roleList);

        List<WaitingUser> waitingUserList = userService.getWaitingUserList(page, size);
        model.addAttribute("waitingUserList", waitingUserList);

        List<WaitingUser> totalWaitingUserList = userService.getWaitingUserList();
        model.addAttribute("totalWaitingUserList", waitingUserList);
        long totalWaitingUsers = totalWaitingUserList.size();

        model.addAttribute("totalWaitingUsers", totalWaitingUsers);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", calculateTotalPages(totalWaitingUsers, size));

        log.info("총 페이지 수:{}", calculateTotalPages(totalWaitingUsers, size));
        log.info("승인 대기 중인 회원 수:{}", totalWaitingUsers);
        log.info("대기 회원 리스트:{}", totalWaitingUserList.isEmpty());


        return "pages/user_approval";
    }

    private int calculateTotalPages(long totalItems, int size) {

        return (int) Math.ceil((double) totalItems / size);
    }

    @PostMapping("/approve")
    public String approveUser(@RequestBody UserApproveRequest request) {

        userService.approveUser(request);

        return "redirect:/user-approval";
    }

    @PostMapping("/reject")
    public String rejectUser(String userId) {

        userService.rejectUser(userId);

        log.info("회원 승인 거절 완료");
        return "redirect:/user-approval";
    }

    @GetMapping("/user-list")
    public String userList() {

        return "pages/user_list";
    }
}
