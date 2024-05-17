package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorRegisterRequest {
    private String sensorType;
    private String sensorName;
    private Integer brokerId;
}
