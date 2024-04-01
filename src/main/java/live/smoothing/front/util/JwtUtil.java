package live.smoothing.front.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;


public class JwtUtil {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Long getExpireTime(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];
        String payloadJson = new String(Base64.getDecoder().decode(payload));
        JsonNode jsonNode = OBJECT_MAPPER.readTree(payloadJson);
        return Long.parseLong(jsonNode.get("exp").asText());
    }

    public static List<String> getRoles(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];
        String payloadJson = new String(Base64.getDecoder().decode(payload));
        JsonNode jsonNode = OBJECT_MAPPER.readTree(payloadJson);
        return OBJECT_MAPPER.readValue(jsonNode.get("roles").toString(), new TypeReference<List<String>>() {
        });
    }

    public static String getUserId(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];
        String payloadJson = new String(Base64.getDecoder().decode(payload));
        JsonNode jsonNode = OBJECT_MAPPER.readTree(payloadJson);
        return jsonNode.get("userId").asText();
    }
}
