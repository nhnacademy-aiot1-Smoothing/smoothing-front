package live.smoothing.front.ai;

import live.smoothing.front.ai.dto.PowerGeneratorLogResponse;
import live.smoothing.front.ai.dto.TimeValue;

import java.util.List;

public interface AiService {

    List<TimeValue> getPowerUsage(String measurement, String field);
    List<TimeValue> getActualPowerUsage(String location, String description);
    List<TimeValue> getPowerGeneration(String measurement, String field);
    List<PowerGeneratorLogResponse> getPowerGeneratorLog(String generatorId);

}
