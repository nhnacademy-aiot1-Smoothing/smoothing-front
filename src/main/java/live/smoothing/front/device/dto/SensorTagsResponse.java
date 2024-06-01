package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class SensorTagsResponse {
    Map<Integer, List<TagResponse>> sensorTags;
}
