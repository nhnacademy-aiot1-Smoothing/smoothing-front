package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrokerErrorResponse {
    private Integer brokerErrorId;
    private String brokerErrorType;
    private String brokerName;
    private String createdAt;
    private String solvedAt;
    private String brokerIp;
    private Integer brokerPort;
}
