package live.smoothing.front.device.service;

import live.smoothing.front.adapter.DeviceAdapter;
import live.smoothing.front.device.dto.SensorErrorListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("sensorErrorService")
public class SensorErrorServiceImpl implements SensorErrorService {

    private final DeviceAdapter deviceAdapter;

    @Override
    public SensorErrorListResponse getSensorErrors(Pageable pageable) {
        return deviceAdapter.getSensorErrors(pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public void deleteSensorError(int sensorErrorId) {
        deviceAdapter.deleteSensorError(sensorErrorId);
    }
}
