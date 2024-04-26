package live.smoothing.front.controller;

import live.smoothing.front.user.dto.response.UserAttendanceResponse;
import live.smoothing.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/attendance")
public class TestRestController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<UserAttendanceResponse> getAttendanceList(@RequestHeader("X-USER-ID") String userId) {

        System.out.println(userService.getUserAttendance(userId).getAttendanceDate().get(0));
        return ResponseEntity.ok().body(userService.getUserAttendance(userId));
    }

}
