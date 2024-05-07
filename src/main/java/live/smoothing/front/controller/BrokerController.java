package live.smoothing.front.controller;

import live.smoothing.front.device.dto.BrokerAddRequest;
import live.smoothing.front.device.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.math.raw.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BrokerController {

    private final BrokerService brokerService;

    @GetMapping("/broker")
    public String broker(Model model) {

        List<String> protocolTypeList = brokerService.getProtocols().getProtocolTypes();
        model.addAttribute("protocolTypeList", protocolTypeList);

        return "pages/broker";
    }

    @ResponseBody
    @PostMapping("/add-broker")
    public void addBroker(@RequestBody BrokerAddRequest request) {

        brokerService.addBroker(request);
    }
}