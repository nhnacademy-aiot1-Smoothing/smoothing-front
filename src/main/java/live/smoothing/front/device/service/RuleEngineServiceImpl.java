package live.smoothing.front.device.service;

import live.smoothing.front.adapter.RuleEngineAdapter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service("ruleEngineService")
public class RuleEngineServiceImpl implements RuleEngineService{

    private static final Logger log = LoggerFactory.getLogger(RuleEngineServiceImpl.class);
    private final RuleEngineAdapter ruleEngineAdapter;

    @Override
    public Map<Integer, String> getRuleEngineStatus() {
        try {
            return ruleEngineAdapter.getBrokerStatus().getBrokerStatus();
        } catch (Exception e) {
            log.error("Failed to get rule engine status", e);
        }
        return Map.of();
    }
}
