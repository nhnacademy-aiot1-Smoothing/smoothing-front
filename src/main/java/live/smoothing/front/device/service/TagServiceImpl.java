package live.smoothing.front.device.service;

import live.smoothing.front.adapter.DeviceAdapter;
import live.smoothing.front.device.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("tagService")
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final DeviceAdapter deviceAdapter;
    @Override
    public void addTag(TagRequest request) {

        deviceAdapter.addTag(request);
    }

    @Override
    public TagListResponse getTags() {

        return deviceAdapter.getTags();
    }

    @Override
    public SensorTagsResponse getSensorTags(SensorIdListRequest sensorIdListRequest) {

        return deviceAdapter.getSensorTags(sensorIdListRequest);
    }

    @Override
    public void addSensorTag(SensorTagAddRequest sensorTagAddRequest) {

        deviceAdapter.addSensorTag(sensorTagAddRequest);
    }

    @Override
    public void deleteSensorTag(Integer sensorTagId) {

        deviceAdapter.deleteSensorTag(sensorTagId);
    }

}
