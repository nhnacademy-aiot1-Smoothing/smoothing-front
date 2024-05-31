package live.smoothing.front.ai;

import live.smoothing.front.adapter.AiAdapter;
import live.smoothing.front.ai.dto.TimeValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiAdapter aiAdapter;

    @Override
    public List<TimeValue> getPowerUsage(String measurement, String field) {
        return aiAdapter.getPowerUsage(measurement, field);
    }

    @Override
    public List<TimeValue> getActualPowerUsage(String location, String description) {
        return aiAdapter.getActualUseData(location, description);
    }

    @Override
    public String getPowerGeneration(String measurement, String field) {
        return aiAdapter.getPowerGeneration(measurement, field);
    }

    @Override
    public String getPowerGeneratorLog(String generatorId) {
        return aiAdapter.getPowerGeneratorLog(generatorId);
    }

}
