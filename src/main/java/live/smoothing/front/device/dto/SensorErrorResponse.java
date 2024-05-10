package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorErrorResponse {
    private Integer sensorErrorId;
    private String sensorErrorType;
    private String sensorName;
    private String createdAt;
    private Double value;
    private String topic;
}
