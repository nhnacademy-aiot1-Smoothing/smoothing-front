package live.smoothing.front.sensor.controller;

import live.smoothing.front.sensor.dto.Igr;
import live.smoothing.front.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IgrController {

    private final SensorService sensorService;

    @GetMapping("/sensor/igr/class")
    public Igr getClassIgr() {

        return sensorService.getClassIgr();
    }

    @GetMapping("/sensor/igr/office")
    public Igr getOfficeIgr() {

        return sensorService.getOfficeIgr();
    }

}
