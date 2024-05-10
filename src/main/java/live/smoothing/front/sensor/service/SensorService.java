package live.smoothing.front.sensor.service;

import live.smoothing.front.adapter.SensorAdapter;
import live.smoothing.front.sensor.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorAdapter sensorAdapter;

    public TagPowerMetricResponse getWatt(String tags, String unit, String per, String type) {

        return sensorAdapter.getWatt(tags, unit, per, type);
    }

    public TimeZoneResponse getWeeklyTimeZone() {

        return sensorAdapter.getWeeklyTimeZone();
    }

    public SensorResponse getWeeklyTotal(String tags) {

        return sensorAdapter.getWeeklyTotal(tags);
    }

    public TagPowerMetricResponse getHourlyTotal(String tags) {

        return sensorAdapter.getHourlyTotal(tags);
    }

    public TagSensorValueResponse getDailyTotalSensorData(String tags) {

        return sensorAdapter.getDailyTotalSensorData(tags);
    }

    public TagPowerMetricResponse getDailyPeriodTotal(String tags, String start, String end) {

        return sensorAdapter.getDailyPeriodTotal(tags, start, end);
    }

}
