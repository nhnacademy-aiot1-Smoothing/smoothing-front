package live.smoothing.front.adapter;

import live.smoothing.front.device.dto.BrokerAddRequest;
import live.smoothing.front.device.dto.BrokerListResponse;
import live.smoothing.front.device.dto.BrokerUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@FeignClient("device-service")
public interface DeviceAdapter {

    @PostMapping("/api/device/brokers")
    public void addBroker(@RequestBody BrokerAddRequest brokerAddRequest);

    @GetMapping("/api/device/brokers")
    public BrokerListResponse getBrokers(@RequestParam("page") int page,
                                         @RequestParam("size") int size);

    @PutMapping("/api/device/brokers/{brokerId}")
    public void updateBroker(@PathVariable("brokerId") Integer brokerId,
                             @RequestBody BrokerUpdateRequest brokerUpdateRequest);

    @DeleteMapping("/api/device/brokers/{brokerId}")
    public void deleteBroker(@PathVariable("brokerId") Integer brokerId);
}
