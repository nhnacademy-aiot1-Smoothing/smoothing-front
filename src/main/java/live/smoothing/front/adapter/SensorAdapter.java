package live.smoothing.front.adapter;

import live.smoothing.front.sensor.dto.TagPowerMetricResponse;
import live.smoothing.front.sensor.dto.TagSensorValueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gateway")
public interface SensorAdapter {

    @GetMapping("/api/sensor/watt/usage")
    TagPowerMetricResponse getWatt(@RequestParam String tags,
                                   @RequestParam String unit,
                                   @RequestParam String per,
                                   @RequestParam String type);

    @GetMapping("/api/sensor/kwh/usage/daily/value/total")
    TagSensorValueResponse getDailyTotalSensorData(@RequestParam String tags);

}
