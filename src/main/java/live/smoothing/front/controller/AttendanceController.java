package live.smoothing.front.controller;

import live.smoothing.front.user.dto.response.UserAttendanceResponse;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final UserService userService;

    @GetMapping
    public String attendance() {

        return "pages/mypage";
    }

    @PostMapping
    public String doAttendanceCheck () {

        userService.doAttendanceCheck();

        return "redirect:/attendance";
    }

    @ResponseBody
    @GetMapping("/list/{year}/{month}")
    public UserAttendanceResponse getUserAttendance(@PathVariable("year") int year, @PathVariable("month") int month) {

        return userService.getUserAttendance(year, month);
    }
}
