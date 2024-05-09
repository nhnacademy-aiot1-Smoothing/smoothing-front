package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorResponse {
    private Integer sensorId;
    private String sensorType;
    private String sensorName;
    private String sensorRegisteredAt;
}
