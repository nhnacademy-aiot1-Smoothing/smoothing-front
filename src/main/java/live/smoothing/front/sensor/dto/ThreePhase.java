package live.smoothing.front.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ThreePhase {
    private List<Phase> top;
    private List<Phase> bottom;
}
