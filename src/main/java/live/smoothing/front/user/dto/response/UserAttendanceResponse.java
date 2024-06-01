package live.smoothing.front.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserAttendanceResponse {

    private String userId;
    private List<LocalDate> attendanceDate;
}
