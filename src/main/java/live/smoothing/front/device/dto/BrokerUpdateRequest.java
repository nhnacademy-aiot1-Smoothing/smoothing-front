package live.smoothing.front.device.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerUpdateRequest {

    private String brokerIp;
    private Integer brokerPort;
    private String brokerName;
    private String protocolType;
}
