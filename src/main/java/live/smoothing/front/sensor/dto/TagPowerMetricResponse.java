package live.smoothing.front.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TagPowerMetricResponse {

    private List<String> tags;
    private List<PowerMetric> data;
}