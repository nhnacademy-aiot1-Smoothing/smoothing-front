package live.smoothing.front.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserAttendanceResponse {

    private String userId;
    private List<LocalDate> attendanceDate;
}