package live.smoothing.front.controller;

import live.smoothing.front.device.dto.BrokerListResponse;
import live.smoothing.front.device.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BrokerController {

    private final BrokerService brokerService;

    @GetMapping("/broker")
    public String broker(@PageableDefault Pageable pageable,
                         Model model) {

        BrokerListResponse brokers = brokerService.getBrokers(pageable);
        model.addAttribute("brokers", brokers.getBrokers());
        return "pages/broker";
    }
}