package live.smoothing.front.email.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 인증번호를 반환하는 DTO
 *
 * @author 김지윤
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificationNumberResponse {

    private String certificationNumber;
}
