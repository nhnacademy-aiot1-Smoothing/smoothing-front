package live.smoothing.front.ai;

import live.smoothing.front.ai.dto.TimeValue;

import java.util.List;

public interface AiService {

    List<TimeValue> getPowerUsage(String measurement, String field);
    List<TimeValue> getActualPowerUsage(String location, String description);
    String getPowerGeneration(String measurement, String field);
    String getPowerGeneratorLog(String generatorId);

}
