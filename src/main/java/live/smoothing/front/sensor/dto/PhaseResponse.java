package live.smoothing.front.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PhaseResponse {

    ThreePhase classA;
    ThreePhase office;
}
