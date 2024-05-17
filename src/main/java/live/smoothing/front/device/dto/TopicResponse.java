package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TopicResponse {
    private Integer topicId;
    private String topicType;
    private String topic;
    private String sensorName;
}
