package live.smoothing.front.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BrokerErrorListResponse {
    private List<BrokerErrorResponse> connectErrors;
    private int totalPage;
}
