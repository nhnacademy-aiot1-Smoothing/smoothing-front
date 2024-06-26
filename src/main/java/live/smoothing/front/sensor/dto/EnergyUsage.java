package live.smoothing.front.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * API 응답을 받는 DTO 클래스*
 *
 * @author 신민석
 */
@Getter
@Setter
public class EnergyUsage {

    private String year;
    private String month;
    private String metro;
    private String city;
    private String biz;
    @JsonProperty("custCnt")
    private String customerCount;
    private String powerUsage;
    private String bill;
    private String unitCost;
}
