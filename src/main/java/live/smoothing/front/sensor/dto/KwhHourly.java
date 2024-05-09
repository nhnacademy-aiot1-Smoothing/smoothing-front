package live.smoothing.front.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class KwhHourly {

    private String type;
    private String unit;
    private String per;
    private Instant time;
    private Double value;
}