package live.smoothing.front.device.service;

import live.smoothing.front.adapter.DeviceAdapter;
import live.smoothing.front.device.dto.BrokerAddRequest;
import live.smoothing.front.device.dto.BrokerListResponse;
import live.smoothing.front.device.dto.BrokerUpdateRequest;
import live.smoothing.front.device.dto.ProtocolTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("brokerService")
@RequiredArgsConstructor
public class BrokerServiceImpl implements BrokerService {

    private final DeviceAdapter deviceAdapter;

    @Override
    public void addBroker(BrokerAddRequest brokerAddRequest) {
        deviceAdapter.addBroker(brokerAddRequest);
    }

    @Override
    public BrokerListResponse getBrokers(Pageable pageable) {
        return deviceAdapter.getBrokers(pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public void updateBroker(Integer brokerId, BrokerUpdateRequest brokerUpdateRequest) {
        deviceAdapter.updateBroker(brokerId, brokerUpdateRequest);
    }

    @Override
    public void deleteBroker(Integer brokerId) {
        deviceAdapter.deleteBroker(brokerId);
    }

    @Override
    public ProtocolTypeResponse getProtocols() {
        return deviceAdapter.getProtocols();
    }
}
