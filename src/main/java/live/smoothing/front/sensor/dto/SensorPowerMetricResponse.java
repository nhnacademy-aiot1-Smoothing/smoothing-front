package live.smoothing.front.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SensorPowerMetricResponse {

    private List<SensorPowerMetric> data;
}