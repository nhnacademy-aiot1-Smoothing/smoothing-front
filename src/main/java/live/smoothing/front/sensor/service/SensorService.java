package live.smoothing.front.sensor.service;

import live.smoothing.front.adapter.SensorAdapter;
import live.smoothing.front.sensor.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public SensorPowerMetricResponse getDailyPeriod(String tags, String start, String end) {

        return sensorAdapter.getDailyPeriod(tags, start, end);
    }

    public CostResponse getCost() {

        return sensorAdapter.getCost();
    }

    public KwhGoalResponse getKwhGoal() {

        return sensorAdapter.getKwhGoal();
    }

    public TagPowerMetricResponse getKwh(String tags, String unit, String per) {

        return sensorAdapter.getKwh(tags, unit, per);
    }

    public List<MonthlyGoalResponse> getMonthlyGoals(String year) {

        return sensorAdapter.getMonthlyGoals(year);
    }

    public EnergyUsageResponse getUsageAverage(int year, String month, String bizCd) {

        return sensorAdapter.getUsageAverage(year, month, bizCd);
    }

    public PhaseResponse getThreePhase() {

        return sensorAdapter.getThreePhase();

    }

    public Igr getClassIgr() {
  
        return sensorAdapter.getClassIgr();
    }

    public Igr getOfficeIgr() {
        return sensorAdapter.getOfficeIgr();
      
    }

    public void setGoal(GoalReqeust goalRequest) {
        sensorAdapter.modifyGoal(goalRequest);
    }
}
