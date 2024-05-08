package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TopicAddRequest {
    private String topic;
    private String topicType;
    private Integer sensorId;
}
