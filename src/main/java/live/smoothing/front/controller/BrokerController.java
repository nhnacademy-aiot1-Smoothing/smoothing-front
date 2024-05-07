package live.smoothing.front.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrokerController {

    @GetMapping("/broker")
    public String broker(@PageableDefault Pageable pageable) {

        return "pages/broker";
    }
}