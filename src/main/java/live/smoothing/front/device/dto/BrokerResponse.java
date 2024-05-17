package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrokerResponse {

    private Integer brokerId;
    private String brokerIp;
    private Integer brokerPort;
    private String brokerName;
    private String protocolType;
}
