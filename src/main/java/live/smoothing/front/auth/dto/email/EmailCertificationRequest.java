package live.smoothing.front.auth.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 이메일 인증번호를 요청하는 DTO
 *
 * @author 김지윤
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCertificationRequest {

    private String userEmail;
}
