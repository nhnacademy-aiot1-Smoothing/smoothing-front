package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.KwhTimeZoneResponse;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import live.smoothing.front.sensor.dto.TagSensorValueResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KwhController {

    private final SensorService sensorService;

    @GetMapping("/sensor/kwh/usage/weekly/timezone")
    public KwhTimeZoneResponse getKwhTimeZone() {

        KwhTimeZoneResponse response = sensorService.getKwhTimeZone();
        System.out.println(response.getData().get(0).getValue());
        System.out.println(response.getData().get(1).getValue());
        System.out.println(response.getData().get(2).getValue());
        System.out.println(response.getData().get(3).getValue());
        return response;
    }

    @GetMapping("/sensor/kwh/daily/value")
    public TagSensorValueResponse getDailyTotalSensorData(@RequestParam String tags) {
        return sensorService.getDailyTotalSensorData(tags);
    }
}
