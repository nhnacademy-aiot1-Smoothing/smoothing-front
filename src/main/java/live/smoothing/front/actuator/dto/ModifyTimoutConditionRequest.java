package live.smoothing.front.actuator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModifyTimoutConditionRequest {
    String eui;
    Long timeout;
}