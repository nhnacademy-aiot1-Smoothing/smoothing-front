package live.smoothing.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    @GetMapping("/statistics")
    public String home() {

        return "pages/statistics";
    }
}
