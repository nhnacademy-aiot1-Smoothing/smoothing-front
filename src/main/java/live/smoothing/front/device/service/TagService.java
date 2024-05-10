package live.smoothing.front.device.service;

import live.smoothing.front.device.dto.*;

public interface TagService {

    void addTag(TagRequest request); // 전체 태그 추가

    void deleteTag(Integer tagId); // 전체 태그 삭제

    void updateTag(Integer tagId, TagRequest request); // 태그 수정

    TagListResponse getTags(); // 전체 태그 가져오기

    SensorTagsResponse getSensorTags(SensorIdListRequest sensorIdListRequest); // 센서의 태그 가져오기

    void addSensorTag(SensorTagAddRequest sensorTagAddRequest); // 센서에 태그 추기

    void deleteSensorTag(Integer sensorTagId); // 센서의 태그 삭제



}
