package live.smoothing.front.adapter;

import live.smoothing.front.device.dto.BrokerAddRequest;
import live.smoothing.front.device.dto.BrokerListResponse;
import live.smoothing.front.device.dto.BrokerUpdateRequest;
import live.smoothing.front.device.dto.ProtocolTypeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@FeignClient("device-service")
public interface DeviceAdapter {

    @PostMapping("/api/device/brokers")
    void addBroker(@RequestBody BrokerAddRequest brokerAddRequest);

    @GetMapping("/api/device/brokers")
    BrokerListResponse getBrokers(@RequestParam("page") int page,
                                         @RequestParam("size") int size);

    @PutMapping("/api/device/brokers/{brokerId}")
    void updateBroker(@PathVariable("brokerId") Integer brokerId,
                             @RequestBody BrokerUpdateRequest brokerUpdateRequest);

    @DeleteMapping("/api/device/brokers/{brokerId}")
    void deleteBroker(@PathVariable("brokerId") Integer brokerId);

    @GetMapping("/api/device/brokers/protocols")
    ProtocolTypeResponse getProtocols();
}
