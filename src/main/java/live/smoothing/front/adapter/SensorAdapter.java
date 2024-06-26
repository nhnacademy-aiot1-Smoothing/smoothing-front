package live.smoothing.front.adapter;

import live.smoothing.front.sensor.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gateway")
public interface SensorAdapter {

    @GetMapping("/api/sensor/watt/usage")
    TagPowerMetricResponse getWatt(@RequestParam String tags,
                                   @RequestParam String unit,
                                   @RequestParam String per,
                                   @RequestParam String type);
    @GetMapping("/api/sensor/kwh/usage/weekly/timezone")
    TimeZoneResponse getWeeklyTimeZone();

    @GetMapping("/api/sensor/kwh/usage/daily/value/total")
    TagSensorValueResponse getDailyTotalSensorData(@RequestParam String tags);


    @GetMapping("/api/sensor/cost")
    CostResponse getCost();

    @GetMapping("/api/sensor/kwh/usage/daily/period/total")
    TagPowerMetricResponse getDailyPeriodTotal(@RequestParam String tags,
                                               @RequestParam String start,
                                               @RequestParam String end);

    @GetMapping("/api/sensor/kwh/usage/daily/period")
    SensorPowerMetricResponse getDailyPeriod(@RequestParam String tags,
                                             @RequestParam String start,
                                             @RequestParam String end);

    @GetMapping("/api/sensor/kwh/usage/weekly/value/total")
    SensorResponse getWeeklyTotal(@RequestParam String tags);

    @GetMapping("/api/sensor/kwh/usage/hourly/total")
    TagPowerMetricResponse getHourlyTotal(@RequestParam String tags);

    @GetMapping("/api/sensor/goals/kwh")
    KwhGoalResponse getKwhGoal();

    @GetMapping("/api/sensor/kwh/usage")
    TagPowerMetricResponse getKwh(@RequestParam String tags,
                                  @RequestParam String unit,
                                  @RequestParam String per);

    @GetMapping("/api/sensor/goals/history")
    List<MonthlyGoalResponse> getMonthlyGoals(@RequestParam String year);


    @GetMapping("/api/sensor/external/usage")
    EnergyUsageResponse getUsageAverage(@RequestParam("year")int year,
                                        @RequestParam("month") String month,
                                        @RequestParam("bizCd") String bizCd);

    @GetMapping("/api/sensor/three-phase")
    PhaseResponse getThreePhase();


    @GetMapping("/api/sensor/igr/class")
    Igr getClassIgr();

    @GetMapping("/api/sensor/igr/office")
    Igr getOfficeIgr();

    @PutMapping("/api/sensor/goals")
    void modifyGoal(@RequestBody GoalReqeust goalRequest);
}
