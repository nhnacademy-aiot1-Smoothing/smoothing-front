package live.smoothing.front.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPointDetailResponse {
    private Long pointDetailId;
    private Long pointDetailAmount;
    private String pointDetailType;
    private LocalDateTime pointRecordDate;
}