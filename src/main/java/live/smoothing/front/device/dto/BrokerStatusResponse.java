package live.smoothing.front.device.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class BrokerStatusResponse {
    private Map<Integer, String> brokerStatus;
}
