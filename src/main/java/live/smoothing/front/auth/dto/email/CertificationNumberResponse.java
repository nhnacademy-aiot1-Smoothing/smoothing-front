package live.smoothing.front.auth.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 인증번호를 반환하는 DTO
 *
 * @author 김지윤
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificationNumberResponse {

    private String certificationNumber;
}
