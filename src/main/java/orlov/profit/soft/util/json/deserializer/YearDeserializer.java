package orlov.profit.soft.util.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Year;

/**
 * Deserializer for parsing JSON strings representing years into Year objects.
 */
public class YearDeserializer extends JsonDeserializer<Year> {

    /**
     * Deserializes a JSON string representing a year into a Year object.
     *
     * @param jsonParser           The JSON parser.
     * @param deserializationContext The deserialization context.
     * @return The parsed Year object.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    @Override
    public Year deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String year = jsonParser.getText();
        return Year.parse(year);
    }
}
