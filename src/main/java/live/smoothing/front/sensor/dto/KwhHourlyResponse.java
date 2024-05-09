package live.smoothing.front.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KwhHourlyResponse {

    private List<String> tags;
    private List<KwhHourly> data;
}