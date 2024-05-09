package live.smoothing.front.device.service;

import live.smoothing.front.device.dto.BrokerAddRequest;
import live.smoothing.front.device.dto.BrokerListResponse;
import live.smoothing.front.device.dto.BrokerUpdateRequest;
import live.smoothing.front.device.dto.ProtocolTypeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

public interface BrokerService {

    void addBroker(BrokerAddRequest brokerAddRequest);

    BrokerListResponse getBrokers(Pageable pageable);

    void updateBroker(Integer brokerId, BrokerUpdateRequest brokerUpdateRequest);

    void deleteBroker(Integer brokerId);

    ProtocolTypeResponse getProtocols();
}
