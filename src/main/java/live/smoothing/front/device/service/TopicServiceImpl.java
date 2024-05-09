package live.smoothing.front.device.service;

import live.smoothing.front.adapter.DeviceAdapter;
import live.smoothing.front.device.dto.TopicAddRequest;
import live.smoothing.front.device.dto.TopicResponseListResponse;
import live.smoothing.front.device.dto.TopicTypeListResponse;
import live.smoothing.front.device.dto.TopicUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("topicService")
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService{

    private final DeviceAdapter deviceAdapter;

    @Override
    public void addTopic(TopicAddRequest topicAddRequest) {
        deviceAdapter.addTopic(topicAddRequest);
    }

    @Override
    public TopicResponseListResponse getTopics(Integer sensorId, Pageable pageable) {
        return deviceAdapter.getTopics(sensorId, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public void updateTopic(Integer topicId, TopicUpdateRequest topicUpdateRequest) {
        deviceAdapter.updateTopic(topicId, topicUpdateRequest);
    }

    @Override
    public void deleteTopic(Integer topicId) {
        deviceAdapter.deleteTopic(topicId);
    }

    @Override
    public TopicTypeListResponse getTopicTypes() {
        return deviceAdapter.getTopicTypes();
    }
}
