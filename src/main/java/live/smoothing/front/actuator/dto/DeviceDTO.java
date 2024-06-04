package live.smoothing.front.actuator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceDTO {

    private String eui;
    private String name;
    private Boolean isActivate;
    private Boolean isManual;
}