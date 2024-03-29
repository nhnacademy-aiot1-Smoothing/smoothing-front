package live.smoothing.front.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;


public class JwtUtil {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Long getExpireTime(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];
        String payloadJson = new String(Base64.getUrlDecoder().decode(payload));
        JsonNode jsonNode = OBJECT_MAPPER.readTree(payloadJson);
        return Long.parseLong(jsonNode.get("exp").asText());
    }
}
