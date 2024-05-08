package live.smoothing.front.sensor.service;

import live.smoothing.front.adapter.SensorAdapter;
import live.smoothing.front.sensor.dto.KwhTimeZoneResponse;
import live.smoothing.front.sensor.dto.TagPowerMetricResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorAdapter sensorAdapter;

    public TagPowerMetricResponse getWatt(String tags, String unit, String per, String type) {
        return sensorAdapter.getWatt(tags, unit, per, type);
    }

    public KwhTimeZoneResponse getKwhTimeZone() {
        return sensorAdapter.getKwhTimeZone();
    }
}
