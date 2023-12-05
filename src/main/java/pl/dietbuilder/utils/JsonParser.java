package pl.dietbuilder.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<String> parsePhotoUrls(String jsonResponse) throws IOException {
        List<String> photoUrls = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        jsonNode.path("photos").forEach(photoNode -> {
            String photoUrl = photoNode.path("src").path("original").asText();
            photoUrls.add(photoUrl);
        });

        return photoUrls;
    }
}
