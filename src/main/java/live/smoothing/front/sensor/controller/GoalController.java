package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.KwhGoalResponse;
import live.smoothing.front.sensor.dto.MonthlyGoalResponse;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoalController {

    private final SensorService sensorService;

    @GetMapping("/sensor/goal/current")
    public KwhGoalResponse getGoal() {

        return sensorService.getKwhGoal();
    }

    @GetMapping("/sensor/goals/history")
    public List<MonthlyGoalResponse> getMonthlyGoals(@RequestParam String year) {

        return sensorService.getMonthlyGoals(year);
    }
}
