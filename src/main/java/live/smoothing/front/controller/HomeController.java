package live.smoothing.front.controller;

import live.smoothing.front.sensor.dto.GoalReqeust;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SensorService goalService;

    @GetMapping("/")
    public String home() {

        return "pages/home";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/device/goal")
    public void setGoal(@RequestBody GoalReqeust goalRequest) {
        goalService.setGoal(goalRequest);
    }
}
