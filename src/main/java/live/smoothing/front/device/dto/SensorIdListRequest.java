package live.smoothing.front.device.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SensorIdListRequest {
    private List<Integer> sensorIds;
}
