package live.smoothing.front.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PowerGeneratorLogResponse {

    private final LocalDateTime time;

    private final String message;
}
