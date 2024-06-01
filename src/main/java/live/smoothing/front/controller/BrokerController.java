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
public class BrokerController {

    private final BrokerService brokerService;

    @GetMapping("/broker")
    public String broker(Pageable pageable,
                         Model model) {
        List<String> protocolTypeList = brokerService.getProtocols().getProtocolTypes();
        model.addAttribute("protocolTypeList", protocolTypeList);

        BrokerListResponse brokers = brokerService.getBrokers(pageable);
        model.addAttribute("brokers", brokers.getBrokers());
        model.addAttribute("size", brokers.getTotalPage());
        model.addAttribute("page", pageable.getPageNumber());
        return "pages/broker";
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/device/brokers")
    public void addBroker(@RequestBody BrokerAddRequest request) {

        brokerService.addBroker(request);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/api/device/brokers/{brokerId}")
    public void updateBroker(@PathVariable Integer brokerId, @RequestBody BrokerUpdateRequest request) {

        brokerService.updateBroker(brokerId, request);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/api/device/brokers/{brokerId}")
    public void deleteBroker(@PathVariable Integer brokerId) {

        brokerService.deleteBroker(brokerId);
    }

}