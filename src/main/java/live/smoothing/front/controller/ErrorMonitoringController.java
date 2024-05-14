package live.smoothing.front.controller;

import live.smoothing.front.device.dto.BrokerErrorListResponse;
import live.smoothing.front.device.service.BrokerErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ErrorMonitoringController {

    private final BrokerErrorService brokerErrorService;

    @GetMapping("/error-monitoring")
    public String errorMonitoring(Pageable pageable,
                                  Model model) {

        BrokerErrorListResponse brokerErrorListResponse = brokerErrorService.getBrokerErrorList(pageable);
        model.addAttribute("brokers", brokerErrorListResponse.getConnectErrors());
        model.addAttribute("size", brokerErrorListResponse.getTotalPage());
        model.addAttribute("page", pageable.getPageNumber());
        return "pages/error_monitoring";
    }

    @ResponseBody
    @DeleteMapping("/api/error-monitoring/{errorId}")
    public void deleteError(@PathVariable("errorId") Integer errorId) {
        brokerErrorService.deleteError(errorId);
    }
}