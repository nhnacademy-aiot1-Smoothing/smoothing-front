package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.KwhGoalResponse;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoalController {

    private final SensorService sensorService;

    @GetMapping("/sensor/goal/current")
    public KwhGoalResponse getGoal() {
        return sensorService.getKwhGoal();
    }
}
