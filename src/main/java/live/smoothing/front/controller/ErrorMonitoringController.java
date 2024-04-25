package live.smoothing.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorMonitoringController {

    @GetMapping("/error-monitoring")
    public String errorMonitoring() {

        return "pages/errorMonitoring";
    }
}