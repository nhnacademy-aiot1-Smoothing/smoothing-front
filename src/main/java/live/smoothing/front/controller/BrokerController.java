package live.smoothing.front.controller;

import live.smoothing.front.device.dto.BrokerAddRequest;
import live.smoothing.front.device.dto.BrokerUpdateRequest;
import live.smoothing.front.device.service.BrokerService;
import lombok.RequiredArgsConstructor;
import live.smoothing.front.device.dto.BrokerListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class BrokerController {

    private final BrokerService brokerService;

    @GetMapping("/broker")
    public String broker(@PageableDefault Pageable pageable,
                         Model model) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        List<String> protocolTypeList = brokerService.getProtocols().getProtocolTypes();
        model.addAttribute("protocolTypeList", protocolTypeList);

        BrokerListResponse brokers = brokerService.getBrokers(pageable);
        model.addAttribute("brokers", brokers.getBrokers());
        return "pages/broker";
    }

    @ResponseBody
    @PostMapping("/api/device/brokers")
    public void addBroker(@RequestBody BrokerAddRequest request) {

        brokerService.addBroker(request);
    }

    @ResponseBody
    @PutMapping("/api/device/brokers/{brokerId}")
    public void updateBroker(@PathVariable Integer brokerId, @RequestBody BrokerUpdateRequest request) {

        brokerService.updateBroker(brokerId, request);
    }

    @ResponseBody
    @DeleteMapping("/api/device/brokers/{brokerId}")
    public void deleteBroker(@PathVariable Integer brokerId) {

        brokerService.deleteBroker(brokerId);
    }

}