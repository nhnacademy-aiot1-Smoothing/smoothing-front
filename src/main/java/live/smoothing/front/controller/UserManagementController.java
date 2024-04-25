package live.smoothing.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagementController {

    @GetMapping("/user-approval")
    public String userApproval() {

        return "pages/userApproval";
    }

    @GetMapping("/user-list")
    public String userList() {

        return "pages/userList";
    }
}
