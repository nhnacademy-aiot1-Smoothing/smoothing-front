package live.smoothing.front.adapter;

import live.smoothing.front.actuator.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "gateway")
public interface ActAdapter {

    @GetMapping("/api/actuator/devices")
    DeviceResponse getDevices();

    @PostMapping("/api/actuator/devices/turn")
    void turnDevice(@RequestBody ControlRequest controlRequest);

    @PostMapping("/api/actuator/devices/control/turn")
    void turnControl(@RequestBody ControlRequest controlRequest);

    @PutMapping("/api/actuator/condition/timeout")
    void modifyTimeout(@RequestBody ModifyTimoutConditionRequest timeoutRequest);

    @GetMapping("/api/actuator/condition/timeout/{eui}")
    TimeoutResponse getTimeout(@PathVariable String eui);

    @GetMapping("/api/actuator/history")
    HistoryResponse getHistory();
}
