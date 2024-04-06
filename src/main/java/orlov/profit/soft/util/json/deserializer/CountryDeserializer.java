package orlov.profit.soft.util.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import orlov.profit.soft.model.Country;

import java.io.IOException;

public class CountryDeserializer extends JsonDeserializer<Country> {

    @Override
    public Country deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String countryName = jsonParser.getText();
        return Country.builder().countryName(countryName).build();
    }
}
