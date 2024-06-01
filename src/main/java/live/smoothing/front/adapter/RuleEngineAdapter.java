package live.smoothing.front.adapter;

import live.smoothing.front.device.dto.BrokerStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gateway")
public interface RuleEngineAdapter {

    @GetMapping("/api/ruleengine/broker/status")
    BrokerStatusResponse getBrokerStatus();
}
