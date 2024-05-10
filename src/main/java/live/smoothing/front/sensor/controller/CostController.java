package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.CostResponse;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CostController {

    private final SensorService costService;

    @GetMapping("/sensor/cost")
    public CostResponse getCost() {
        return costService.getCost();
    }
}
