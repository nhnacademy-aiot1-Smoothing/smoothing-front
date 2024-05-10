package live.smoothing.front.device.service;

import live.smoothing.front.device.dto.SensorErrorListResponse;
import org.springframework.data.domain.Pageable;

public interface SensorErrorService {
    SensorErrorListResponse getSensorErrors(Pageable pageable);
    void deleteSensorError(int sensorErrorId);
}
