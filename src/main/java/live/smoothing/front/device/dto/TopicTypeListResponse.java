package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TopicTypeListResponse {
    private List<TopicTypeResponse> topicTypes;
}
