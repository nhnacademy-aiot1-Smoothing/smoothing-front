package live.smoothing.front.device.service;

import live.smoothing.front.device.dto.SensorListResponse;
import live.smoothing.front.device.dto.SensorRegisterRequest;
import live.smoothing.front.device.dto.SensorTypeListResponse;
import live.smoothing.front.device.dto.SensorUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface SensorService {
    void addSensor(SensorRegisterRequest sensorRegisterRequest);

    SensorListResponse getSensor(Integer brokerId, Pageable pageable);

    void updateSensor(Integer sensorId, SensorUpdateRequest sensorUpdateRequest);

    void deleteSensor(Integer sensorId);

    SensorTypeListResponse getSensorTypes();
}
