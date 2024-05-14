package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SensorListResponse {
    private List<SensorResponse> sensors;
    private int totalPage;
}
