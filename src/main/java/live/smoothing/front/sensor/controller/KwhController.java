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
    public TimeZoneResponse getWeeklyTimeZone() {

        return sensorService.getWeeklyTimeZone();
    }

    @GetMapping("/sensor/kwh/usage/weekly/value/total")
    public SensorResponse getWeeklyTotal(@RequestParam String tags) {

        return sensorService.getWeeklyTotal(tags);
    }

    @GetMapping("sensor/kwh/usage/hourly/total")
    public TagPowerMetricResponse getKwhHourly(@RequestParam String tags) {

        return sensorService.getHourlyTotal(tags);
    }

    @GetMapping("/sensor/kwh/daily/value")
    public TagSensorValueResponse getDailyTotalSensorData(@RequestParam String tags) {

        return sensorService.getDailyTotalSensorData(tags);
    }

    @GetMapping("/sensor/kwh/usage/daily/period/total")
    public TagPowerMetricResponse getDailyPeriodTotal(@RequestParam("tags") String tags, @RequestParam("start") String start, @RequestParam("end") String end) {

        return sensorService.getDailyPeriodTotal(tags, start, end);
    }
}
