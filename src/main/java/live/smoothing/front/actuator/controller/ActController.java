package live.smoothing.front.actuator.controller;

import live.smoothing.front.actuator.dto.*;
import live.smoothing.front.actuator.service.ActService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actuator")
public class ActController {

    private final ActService actService;

    @GetMapping("/devices")
    public DeviceResponse getDevices() {
        return actService.getDevices();
    }

    @GetMapping("/history")
    public HistoryResponse getHistory() {
        return actService.getHistory();
    }

    @GetMapping("/timeout/{eui}")
    public TimeoutResponse getTimeout(@PathVariable String eui) {
        return actService.getTimeout(eui);
    }

    @PutMapping("/timeout")
    public void modifyTimeout(@RequestBody ModifyTimoutConditionRequest timeoutRequest) {
        actService.modifyTimeout(timeoutRequest);
    }

    @PostMapping("/devices/turn")
    public void turnDevice(@RequestBody ControlRequest controlRequest) {
        actService.turnDevice(controlRequest);
    }

    @PostMapping("/devices/control/turn")
    public void turnControl(@RequestBody ControlRequest controlRequest) {
        actService.turnControl(controlRequest);
    }
}
