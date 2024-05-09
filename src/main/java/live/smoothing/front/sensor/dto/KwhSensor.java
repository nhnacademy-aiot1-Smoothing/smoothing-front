package live.smoothing.front.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KwhSensor {

    private String sensorName;
    private Double value;
}
