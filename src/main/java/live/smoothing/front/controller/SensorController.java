package live.smoothing.front.controller;

import live.smoothing.front.device.dto.*;
import live.smoothing.front.device.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping("/broker/{brokerId}/sensor")
    public String sensor(@PageableDefault(size = 100) Pageable pageable,
                         @PathVariable("brokerId") Integer brokerId,
                         @RequestParam("broker") String broker,
                         Model model) {
        SensorTypeListResponse sensorTypeListResponse = sensorService.getSensorTypes();
        SensorListResponse sensors = sensorService.getSensor(brokerId, pageable);

        model.addAttribute("sensorTypes", sensorTypeListResponse.getSensorTypes().stream().map(SensorTypeResponse::getSensorType).collect(Collectors.toList()));//refactor
        model.addAttribute("sensors", sensors.getSensors());
        model.addAttribute("brokerId", brokerId);
        model.addAttribute("broker", broker);
        return "pages/sensor";
    }

    @ResponseBody
    @PostMapping("/api/device/sensors")
    public void addSensor(@RequestBody SensorRegisterRequest sensorRegisterRequest) {
        sensorService.addSensor(sensorRegisterRequest);
    }

    @ResponseBody
    @PutMapping("/api/device/sensors/{sensorId}")
    public void updateSensor(@PathVariable Integer sensorId, @RequestBody SensorUpdateRequest sensorUpdateRequest) {
        sensorService.updateSensor(sensorId, sensorUpdateRequest);
    }

    @ResponseBody
    @DeleteMapping("/api/device/sensors/{sensorId}")
    public void deleteSensor(@PathVariable Integer sensorId) {
        sensorService.deleteSensor(sensorId);
    }
}
