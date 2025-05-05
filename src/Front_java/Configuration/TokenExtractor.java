package Front_java.Configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenExtractor {

	public static String extractToken(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode tokenNode = rootNode.path("token");

            if (!tokenNode.isMissingNode()) {
                return tokenNode.asText();
            } else {
                throw new RuntimeException("Le token n'a pas été trouvé dans la réponse JSON.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}