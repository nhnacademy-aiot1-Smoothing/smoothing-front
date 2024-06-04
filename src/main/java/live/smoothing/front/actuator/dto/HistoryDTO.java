package live.smoothing.front.actuator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HistoryDTO {

    private String eui;
    private String time;
    private String message;
}