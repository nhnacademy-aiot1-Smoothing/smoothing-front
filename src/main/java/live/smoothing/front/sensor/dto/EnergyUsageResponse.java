package live.smoothing.front.sensor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnergyUsageResponse {

    private EnergyUsage wholeCountry;
    private EnergyUsage kimCity;
}
