package orlov.profit.soft.util.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import orlov.profit.soft.model.Country;

import java.io.IOException;

/**
 * Deserializer for parsing JSON strings representing countries into Country objects.
 */
public class CountryDeserializer extends JsonDeserializer<Country> {

    /**
     * Deserializes a JSON string representing a country into a Country object.
     *
     * @param jsonParser           The JSON parser.
     * @param deserializationContext The deserialization context.
     * @return The parsed Country object.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    @Override
    public Country deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String countryName = jsonParser.getText();
        return Country.builder().countryName(countryName).build();
    }
}
