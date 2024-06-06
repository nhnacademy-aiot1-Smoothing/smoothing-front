package live.smoothing.front.controller;

import live.smoothing.front.device.dto.*;
import live.smoothing.front.device.service.SensorService;
import live.smoothing.front.device.service.TagService;
import live.smoothing.front.sensor.dto.GoalReqeust;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;
    private final TagService tagService;

    @GetMapping("/broker/{brokerId}/sensor")
    public String sensor(Pageable pageable,
                         @PathVariable("brokerId") Integer brokerId,
                         @RequestParam("broker") String broker,
                         Model model) {
        SensorTypeListResponse sensorTypeListResponse = sensorService.getSensorTypes();
        SensorListResponse sensors = sensorService.getSensor(brokerId, pageable);

        model.addAttribute("sensorTypes", sensorTypeListResponse.getSensorTypes().stream().map(SensorTypeResponse::getSensorType).collect(Collectors.toList()));//refactor
        model.addAttribute("sensors", sensors.getSensors());
        model.addAttribute("brokerId", brokerId);
        model.addAttribute("broker", broker);
        model.addAttribute("size", sensors.getTotalPage());
        model.addAttribute("page", pageable.getPageNumber());

        List<TagResponse> tagList = tagService.getTags().getTags();
        model.addAttribute("tagList", tagList);

         SensorTagsResponse sensorTags = tagService.getSensorTags(new SensorIdListRequest(sensors.getSensors().stream().map(SensorResponse::getSensorId).collect(Collectors.toList())));

         model.addAttribute("sensorTags", sensorTags.getSensorTags());

        return "pages/sensor";
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/device/sensors")
    public void addSensor(@RequestBody SensorRegisterRequest sensorRegisterRequest) {
        sensorService.addSensor(sensorRegisterRequest);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/api/device/sensors/{sensorId}")
    public void updateSensor(@PathVariable Integer sensorId, @RequestBody SensorUpdateRequest sensorUpdateRequest) {
        sensorService.updateSensor(sensorId, sensorUpdateRequest);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/api/device/sensors/{sensorId}")
    public void deleteSensor(@PathVariable Integer sensorId) {
        sensorService.deleteSensor(sensorId);
    }

    @ResponseBody
    @PostMapping("/addTag")
    public void addTag(@RequestBody TagRequest request) {

        tagService.addTag(request);
    }

    @ResponseBody
    @PostMapping("/addSensorTag")
    public void addSensorTag(@RequestBody SensorTagAddRequest sensorTagAddRequest) {

        tagService.addSensorTag(sensorTagAddRequest);
    }

    @ResponseBody
    @DeleteMapping("/deleteTag/{tagId}")
    public void deleteTag(@PathVariable("tagId") Integer tagId) {

        tagService.deleteTag(tagId);
    }

    @ResponseBody
    @PutMapping("/updateTag/{tagId}")
    public void updateTag(@PathVariable("tagId") Integer tagId, @RequestBody TagRequest request) {

        tagService.updateTag(tagId, request);
    }

    @ResponseBody
    @DeleteMapping("/deleteSensorTag/{sensorId}/{tagId}")
    public void deleteSensorTag(@PathVariable Integer sensorId, @PathVariable Integer tagId) {

        tagService.deleteSensorTag(sensorId, tagId);
    }

    @ResponseBody
    @GetMapping("/tags")
    public List<TagResponse> getTags() {
        return tagService.getTags().getTags();
    }

    @ResponseBody
    @PostMapping("/api/sensor/goal")
    public void setGoal(@RequestBody GoalReqeust goalRequest) {
        sensorService.modifyGoal(goalRequest);
    }
}
