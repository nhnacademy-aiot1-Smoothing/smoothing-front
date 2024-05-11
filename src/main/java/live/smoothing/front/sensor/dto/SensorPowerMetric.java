package live.smoothing.front.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SensorPowerMetric {

    private String sensorName;
    private List<PowerMetric> powerMetrics;
}
