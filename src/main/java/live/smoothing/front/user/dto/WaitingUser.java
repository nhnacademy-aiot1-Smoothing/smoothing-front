package live.smoothing.front.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WaitingUser {

    private String userId;
    private String userName;
    private LocalDateTime requestDate;
}
