package live.smoothing.front.controller;

import live.smoothing.front.device.dto.*;
import live.smoothing.front.device.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/broker/{brokerId}/sensor/{sensorId}/topic")
    public String topic(@PageableDefault Pageable pageable,
                        @PathVariable(value = "sensorId") Integer sensorId,
                        @PathVariable(value = "brokerId") Integer brokerId,
                        @RequestParam(value = "sensor") String sensor,
                        Model model) {
        TopicTypeListResponse sensorTypeListResponse = topicService.getTopicTypes();
        TopicResponseListResponse topicResponseListResponse = topicService.getTopics(sensorId, pageable);
        model.addAttribute("topicTypes", sensorTypeListResponse.getTopicTypes().stream().map(TopicTypeResponse::getTopicType).collect(Collectors.toList()));
        model.addAttribute("topics", topicResponseListResponse.getTopics());
        model.addAttribute("sensorId", sensorId);
        model.addAttribute("brokerId", brokerId);
        model.addAttribute("sensor", sensor);
        return "pages/topic";
    }

    @ResponseBody
    @PostMapping("/api/device/topics")
    public void addTopic(@RequestBody TopicAddRequest topicAddRequest) {
        topicService.addTopic(topicAddRequest);
    }

    @ResponseBody
    @PutMapping("/api/device/topics/{topicId}")
    public void updateTopic(@PathVariable Integer topicId, @RequestBody TopicUpdateRequest topicUpdateRequest) {
        topicService.updateTopic(topicId, topicUpdateRequest);
    }

    @ResponseBody
    @DeleteMapping("/api/device/topics/{topicId}")
    public void deleteTopic(@PathVariable Integer topicId) {
        topicService.deleteTopic(topicId);
    }
}
