package live.smoothing.front.adapter;

import live.smoothing.front.device.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@FeignClient("device-service")
public interface DeviceAdapter {

    @PostMapping("/api/device/brokers")
    void addBroker(@RequestBody BrokerAddRequest brokerAddRequest);

    @GetMapping("/api/device/brokers")
    BrokerListResponse getBrokers(@RequestParam("page") int page,
                                         @RequestParam("size") int size);

    @PutMapping("/api/device/brokers/{brokerId}")
    void updateBroker(@PathVariable("brokerId") Integer brokerId,
                             @RequestBody BrokerUpdateRequest brokerUpdateRequest);

    @DeleteMapping("/api/device/brokers/{brokerId}")
    void deleteBroker(@PathVariable("brokerId") Integer brokerId);

    @GetMapping("/api/device/brokers/protocols")
    ProtocolTypeResponse getProtocols();

    @GetMapping("/api/device/sensors/types")
    SensorTypeListResponse getSensorTypes();

    @PostMapping("/api/device/sensors")
    void addSensor(@RequestBody SensorRegisterRequest sensorRegisterRequest);

    @GetMapping("/api/device/sensors/{brokerId}")
    SensorListResponse getSensor(@PathVariable("brokerId") Integer brokerId,
                                 @RequestParam("page") int page,
                                 @RequestParam("size") int size);

    @PutMapping("/api/device/sensors/{sensorId}")
    void updateSensor(@PathVariable("sensorId") Integer sensorId,
                      @RequestBody SensorUpdateRequest sensorUpdateRequest);

    @DeleteMapping("/api/device/sensors/{sensorId}")
    void deleteSensor(@PathVariable("sensorId") Integer sensorId);

    @PostMapping("/api/device/tags")
    void addTag(@RequestHeader("X-USER-ID") String userId,
                @RequestBody TagRequest tagRequest);

    @GetMapping("/api/device/tags")
    TagListResponse getTags();

    @PutMapping("/api/device/tags/{tagId}")
    void updateTag(@PathVariable Integer tagId,
                   @RequestBody TagRequest tagRequest);

    @DeleteMapping("/api/device/tags/{tagId}")
    void deleteTag(@PathVariable Integer tagId);

    @PostMapping("/api/device/topics")
    void addTopic(@RequestBody TopicAddRequest topicAddRequest);

    @GetMapping("/api/device/topics/{sensorId}")
    TopicResponseListResponse getTopics(@PathVariable("sensorId") Integer sensorId,
                                        @RequestParam("page") int page,
                                        @RequestParam("size") int size);

    @PutMapping("/api/device/topics/{topicId}")
    void updateTopic(@PathVariable("topicId") Integer topicId,
                     @RequestBody TopicUpdateRequest topicUpdateRequest);

    @DeleteMapping("/api/device/topics/{topicId}")
    void deleteTopic(@PathVariable("topicId") Integer topicId);

    @GetMapping("/api/device/topics/types")
    TopicTypeListResponse getTopicTypes();

}
