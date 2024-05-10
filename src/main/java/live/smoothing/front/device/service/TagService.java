package live.smoothing.front.device.service;

import live.smoothing.front.device.dto.SensorIdListRequest;
import live.smoothing.front.device.dto.SensorTagsResponse;
import live.smoothing.front.device.dto.TagListResponse;
import live.smoothing.front.device.dto.TagRequest;

public interface TagService {

    void addTag(TagRequest request);

    TagListResponse getTags();

    SensorTagsResponse getSensorTags(SensorIdListRequest sensorIdListRequest);
}
