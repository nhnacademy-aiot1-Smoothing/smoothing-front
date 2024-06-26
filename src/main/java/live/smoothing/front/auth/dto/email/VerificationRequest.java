package live.smoothing.front.auth.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일, 인증번호 검증 요청 DTO
 *
 * @author 김지윤
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequest {

    private String userEmail;
    private String certificationNumber;
}
