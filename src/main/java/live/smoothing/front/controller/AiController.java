package live.smoothing.front.controller;

import live.smoothing.front.adapter.AiAdapter;
import live.smoothing.front.ai.dto.PowerGeneratorLogResponse;
import live.smoothing.front.ai.dto.TimeValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AiController {

    private final AiAdapter aiAdapter;

    @GetMapping("/ai")
    public String aiPage() {

        return "pages/ai_prediction";
    }

    @ResponseBody
    @GetMapping("/ai/power-usage/prediction")
    public List<TimeValue> getPredictedPowerUsage(@RequestParam String measurement, @RequestParam String field) {
        return aiAdapter.getPowerUsage(measurement, field);
    }

    @ResponseBody
    @GetMapping("/ai/power-usage/actual")
    public List<TimeValue> getActualPowerUsage(@RequestParam String location, @RequestParam String description) {
        return aiAdapter.getActualUseData(location, description);
    }

    @ResponseBody
    @GetMapping("/ai/power-generation")
    public List<TimeValue> getPowerGeneration(@RequestParam String measurement, @RequestParam String field) {
        return aiAdapter.getPowerGeneration(measurement, field);
    }

    @ResponseBody
    @GetMapping("/ai/power-generator/log")
    public List<PowerGeneratorLogResponse> getPowerGenerationLog(@RequestParam String generatorId) {
        return aiAdapter.getPowerGeneratorLog(generatorId);
    }

}
