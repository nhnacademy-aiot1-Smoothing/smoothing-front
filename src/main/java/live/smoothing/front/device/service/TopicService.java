package live.smoothing.front.device.service;

import live.smoothing.front.device.dto.TopicAddRequest;
import live.smoothing.front.device.dto.TopicResponseListResponse;
import live.smoothing.front.device.dto.TopicTypeListResponse;
import live.smoothing.front.device.dto.TopicUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface TopicService {
    void addTopic(TopicAddRequest topicAddRequest);
    TopicResponseListResponse getTopics(Integer sensorId, Pageable pageable);
    void updateTopic(Integer topicId, TopicUpdateRequest topicUpdateRequest);
    void deleteTopic(Integer topicId);
    TopicTypeListResponse getTopicTypes();
}
