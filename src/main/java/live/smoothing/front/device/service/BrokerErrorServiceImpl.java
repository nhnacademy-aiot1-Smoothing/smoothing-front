package live.smoothing.front.device.service;

import live.smoothing.front.adapter.DeviceAdapter;
import live.smoothing.front.device.dto.BrokerErrorListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("brokerErrorService")
public class BrokerErrorServiceImpl implements BrokerErrorService{

    private final DeviceAdapter deviceAdapter;

    @Override
    public BrokerErrorListResponse getBrokerErrorList(Pageable pageable) {
        return deviceAdapter.getErrors(pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public void deleteError(Integer errorId) {
        deviceAdapter.deleteError(errorId);
    }
}
