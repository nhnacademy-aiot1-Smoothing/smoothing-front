package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.*;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KwhController {

    private final SensorService sensorService;

    @GetMapping("/sensor/kwh/usage/weekly/timezone")
    public KwhTimeZoneResponse getKwhTimeZone() {

        return sensorService.getKwhTimeZone();
    }

    @GetMapping("/sensor/kwh/usage/weekly/value/total")
    public KwhSensorResponse getKwhSensor(@RequestParam String tags) {

        return sensorService.getKwhSensor(tags);
    }

    @GetMapping("sensor/kwh/usage/hourly/total")
    public KwhHourlyResponse getKwhHourly(@RequestParam String tags) {

        return sensorService.getKwhHourly(tags);
    }

    @GetMapping("/sensor/kwh/daily/value")
    public TagSensorValueResponse getDailyTotalSensorData(@RequestParam String tags) {

        return sensorService.getDailyTotalSensorData(tags);
    }
}