package live.smoothing.front.actuator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    List<HistoryDTO> histories;
}