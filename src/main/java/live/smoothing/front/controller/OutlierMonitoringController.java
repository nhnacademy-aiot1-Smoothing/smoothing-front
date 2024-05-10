package live.smoothing.front.controller;

import live.smoothing.front.device.dto.SensorErrorListResponse;
import live.smoothing.front.device.service.SensorErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class OutlierMonitoringController {

    private final SensorErrorService sensorErrorService;

    @GetMapping("/outlier-monitoring")
    public String outlier(@PageableDefault(size = 1000) Pageable pageable,
                          Model model) {

        SensorErrorListResponse sensorErrors = sensorErrorService.getSensorErrors(pageable);
        model.addAttribute("errors", sensorErrors.getErrors());

        return "pages/outlier_monitoring";
    }

    @ResponseBody
    @DeleteMapping("/api/outlier-monitoring/{errorId}")
    public void deleteError(@PathVariable("errorId") Integer errorId) {
        sensorErrorService.deleteSensorError(errorId);
    }
}