package live.smoothing.front.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.List;


/**
 * JWT Token 관련 유틸 클래스
 *
 * @author 박영준, 우혜승
 */
public class JwtUtil {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * JWT Token에서 만료시간을 추출하는 메서드
     *
     * @param token 문자열 JWT Token
     * @return 만료시간
     * @throws JsonProcessingException Json 파싱 중 오류 발생 예외
     */
    public static Long getExpireTime(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];
        String payloadJson = new String(Base64.getDecoder().decode(payload));
        JsonNode jsonNode = OBJECT_MAPPER.readTree(payloadJson);
        return Long.parseLong(jsonNode.get("exp").asText());
    }

    /**
     * JWT Token에서 권한을 추출하는 메서드
     *
     * @param token 문자열 JWT Token
     * @return 권한 리스트
     * @throws JsonProcessingException Json 파싱 중 오류 발생 예외
     */
    public static List<String> getRoles(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];
        String payloadJson = new String(Base64.getDecoder().decode(payload));
        JsonNode jsonNode = OBJECT_MAPPER.readTree(payloadJson);
        return OBJECT_MAPPER.readValue(jsonNode.get("roles").toString(), new TypeReference<List<String>>() {
        });
    }

    /**
     * Jwt Token에서 유저 아이디를 추출하는 메서드
     *
     * @param token 문자열 JWT Token
     * @return 유저 아이디
     * @throws JsonProcessingException Json 파싱 중 오류 발생 예외
     */
    public static String getUserId(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];
        String payloadJson = new String(Base64.getDecoder().decode(payload));
        JsonNode jsonNode = OBJECT_MAPPER.readTree(payloadJson);
        return jsonNode.get("userId").asText();
    }
}
