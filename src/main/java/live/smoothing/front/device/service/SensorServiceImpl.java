package live.smoothing.front.device.service;

import live.smoothing.front.adapter.DeviceAdapter;
import live.smoothing.front.adapter.SensorAdapter;
import live.smoothing.front.device.dto.SensorListResponse;
import live.smoothing.front.device.dto.SensorRegisterRequest;
import live.smoothing.front.device.dto.SensorTypeListResponse;
import live.smoothing.front.device.dto.SensorUpdateRequest;
import live.smoothing.front.sensor.dto.GoalReqeust;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("deviceSensorService")
public class SensorServiceImpl implements SensorService{

    private final DeviceAdapter deviceAdapter;
    private final SensorAdapter sensorAdapter;

    @Override
    public void addSensor(SensorRegisterRequest sensorRegisterRequest) {
        deviceAdapter.addSensor(sensorRegisterRequest);
    }

    @Override
    public SensorListResponse getSensor(Integer brokerId, Pageable pageable) {
        return deviceAdapter.getSensor(brokerId, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public void updateSensor(Integer sensorId, SensorUpdateRequest sensorUpdateRequest) {
        deviceAdapter.updateSensor(sensorId, sensorUpdateRequest);
    }

    @Override
    public void deleteSensor(Integer sensorId) {
        deviceAdapter.deleteSensor(sensorId);
    }

    @Override
    public SensorTypeListResponse getSensorTypes() {
        return deviceAdapter.getSensorTypes();
    }

    @Override
    public void modifyGoal(GoalReqeust goalRequest) {
        sensorAdapter.modifyGoal(goalRequest);
    }
}
