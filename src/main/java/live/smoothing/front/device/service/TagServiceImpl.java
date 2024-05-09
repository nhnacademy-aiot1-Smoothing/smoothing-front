package live.smoothing.front.device.service;

import live.smoothing.front.adapter.DeviceAdapter;
import live.smoothing.front.device.dto.TagListResponse;
import live.smoothing.front.device.dto.TagRequest;
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
}
