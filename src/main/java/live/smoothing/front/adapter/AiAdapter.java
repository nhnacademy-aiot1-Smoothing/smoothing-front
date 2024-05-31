package live.smoothing.front.adapter;

import live.smoothing.front.ai.dto.TimeValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// gateway 라우팅 추가 하는 부분 배우기
@FeignClient("gateway")
public interface AiAdapter {

    @GetMapping("/api/ai")
    List<TimeValue> getPowerUsage(@RequestParam("measurement") String measurement,
                                  @RequestParam("field") String field);

    @GetMapping("/api/ai/actual-use-data")
    List<TimeValue> getActualUseData(@RequestParam("location") String location,
                            @RequestParam("description") String description);

    @GetMapping("/api/ai/power-generation")
    String getPowerGeneration(@RequestParam("measurement") String measurement,
                              @RequestParam("field") String field);

    @GetMapping("/api/ai/power-generator/log")
    String getPowerGeneratorLog(@RequestParam("generatorId") String generatorId);
}

