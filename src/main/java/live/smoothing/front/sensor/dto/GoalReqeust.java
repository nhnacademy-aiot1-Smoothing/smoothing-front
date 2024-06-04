package live.smoothing.front.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoalReqeust {
    @JsonProperty("goalAmount")
    private Double goalAmount;
    @JsonProperty("unitPrice")
    private Integer unitPrice;
}
