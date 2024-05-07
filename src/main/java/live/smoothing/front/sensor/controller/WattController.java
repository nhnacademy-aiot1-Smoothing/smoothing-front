package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.TagPowerMetricResponse;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WattController {

    private final SensorService sensorService;

    @GetMapping("/sensor/watt")
    public TagPowerMetricResponse getWatt(@RequestParam String tags,
                                          @RequestParam String unit,
                                          @RequestParam String per,
                                          @RequestParam String type) {

        return sensorService.getWatt(tags, unit, per, type);
    }
}
