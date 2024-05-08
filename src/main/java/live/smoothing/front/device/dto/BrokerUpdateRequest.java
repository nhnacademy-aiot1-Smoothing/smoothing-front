package live.smoothing.front.device.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class BrokerUpdateRequest {

    private String brokerIp;
    private Integer brokerPort;
    private String brokerName;
    private String protocolType;
}
