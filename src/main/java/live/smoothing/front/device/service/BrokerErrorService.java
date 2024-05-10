package live.smoothing.front.device.service;

import live.smoothing.front.device.dto.BrokerErrorListResponse;
import org.springframework.data.domain.Pageable;

public interface BrokerErrorService {
    BrokerErrorListResponse getBrokerErrorList(Pageable pageable);

    void deleteError(Integer errorId);
}
