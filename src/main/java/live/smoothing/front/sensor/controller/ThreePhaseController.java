package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.PhaseResponse;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ThreePhaseController {

    private final SensorService sensorService;

    @GetMapping("/sensor/three-phase")
    public PhaseResponse getThreePhase() {

        return sensorService.getThreePhase();
    }
}
