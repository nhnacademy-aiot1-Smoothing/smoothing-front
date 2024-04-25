package live.smoothing.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OutlierMonitoringController {

    @GetMapping("/outlier-monitoring")
    public String outlier() {

        return "pages/outlierMonitoring";
    }
}