package live.smoothing.front.actuator.service;

import live.smoothing.front.actuator.dto.*;
import live.smoothing.front.adapter.ActAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActService {

    private final ActAdapter actAdapter;

    public DeviceResponse getDevices() {
        return actAdapter.getDevices();
    }

    public void turnDevice(ControlRequest controlRequest) {
        actAdapter.turnDevice(controlRequest);
    }

    public void turnControl(ControlRequest controlRequest) {
        actAdapter.turnControl(controlRequest);
    }

    public void modifyTimeout(ModifyTimoutConditionRequest timeoutRequest) {
        actAdapter.modifyTimeout(timeoutRequest);
    }

    public TimeoutResponse getTimeout(String eui) {
        return actAdapter.getTimeout(eui);
    }

    public HistoryResponse getHistory() {
        return actAdapter.getHistory();
    }
}
