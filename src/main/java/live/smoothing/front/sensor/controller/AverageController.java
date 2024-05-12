package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.EnergyUsageResponse;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AverageController {

    private final SensorService sensorService;

    @GetMapping("/api/sensor/external/usage")
    public EnergyUsageResponse getAverage(
//            @RequestParam int year,
//                                          @RequestParam String month,
//                                          @RequestParam String bizCd
    ) {
        return sensorService.getUsageAverage(2023, "11", "P");
//        return sensorService.getUsageAverage(year, month, bizCd);
    }
}
