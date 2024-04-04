package orlov.profit.soft.util.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;
import java.io.IOException;

public class JsonStreamProcessor {
    public static void main(String[] args) {
        File file = new File("src/main/resources/cities.json");

        try (JsonParser jsonParser = new JsonFactory().createParser(file)) {
            while (jsonParser.nextToken() != null) {
                if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
                    processJsonObject(jsonParser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processJsonObject(JsonParser jsonParser) throws IOException {
        // Process JSON object
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken(); // Move to the value token
            String value = jsonParser.getText(); // Get the field value
            System.out.println(fieldName + ": " + value);
        }
        System.out.println(); // Separate objects
    }
}