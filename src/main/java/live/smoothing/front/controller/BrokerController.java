package live.smoothing.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrokerController {

    @GetMapping("/broker")
    public String broker() {

        return "pages/broker";
    }
}