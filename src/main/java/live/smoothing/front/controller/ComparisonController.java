package live.smoothing.front.controller;

import live.smoothing.front.device.dto.TagListResponse;
import live.smoothing.front.device.service.BrokerService;
import live.smoothing.front.device.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ComparisonController {

    private final TagService tagService;

    @GetMapping("/comparison")
    public String comparisonPage() {

        return "/pages/comparison";
    }

    @ResponseBody
    @GetMapping("/api/device/tags")
    public TagListResponse getTags() {

        return tagService.getTags();
    }

}
