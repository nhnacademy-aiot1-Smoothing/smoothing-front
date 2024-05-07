package live.smoothing.front.device.service;

import live.smoothing.front.device.dto.BrokerAddRequest;
import live.smoothing.front.device.dto.BrokerListResponse;
import live.smoothing.front.device.dto.BrokerUpdateRequest;

public interface BrokerService {

    void addBroker(BrokerAddRequest brokerAddRequest);

    BrokerListResponse getBrokers(int page, int size);

    void updateBroker(Integer brokerId, BrokerUpdateRequest brokerUpdateRequest);

    void deleteBroker(Integer brokerId);
}
