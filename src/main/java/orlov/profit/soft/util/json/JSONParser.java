package orlov.profit.soft.util.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import orlov.profit.soft.model.City;
import orlov.profit.soft.model.Country;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A utility class for parsing JSON data into Java objects and vice versa.
 */
public class JSONParser {

    private final JsonFactory jsonFactory;

    /**
     * Constructs a new JSONParser object.
     */
    public JSONParser() {
        this.jsonFactory = new JsonFactory();
    }

    /**
     * Creates a JSON representation of a City object.
     *
     * @param city The City object to be converted to JSON.
     * @return The JSON representation of the City object.
     * @throws IOException If an I/O error occurs during JSON creation.
     */
    public String createCityJson(City city) throws IOException {
        Objects.requireNonNull(city, "City can't be null");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (JsonGenerator jsonGenerator = createJsonGenerator(stream)) {
            writeCityObject(jsonGenerator, city);
        }

        return stream.toString(StandardCharsets.UTF_8);
    }

    private JsonGenerator createJsonGenerator(ByteArrayOutputStream stream) throws IOException {
        return jsonFactory.createGenerator(stream, JsonEncoding.UTF8);
    }

    private void writeCityObject(JsonGenerator jsonGenerator, City city) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("cityName", city.getCityName());
        writeCountryField(jsonGenerator, city.getCountry());
        jsonGenerator.writeNumberField("cityPopulation", city.getCityPopulation());
        jsonGenerator.writeNumberField("cityArea", city.getCityArea());
        writeFoundedAtField(jsonGenerator, city.getFoundedAt());

        writeLanguagesField(jsonGenerator, city.getLanguages());

        jsonGenerator.writeEndObject();
    }

    private void writeCountryField(JsonGenerator jsonGenerator, Country country) throws IOException {
        if (country != null) {
            jsonGenerator.writeStringField("country", country.getCountryName());
        } else {
            jsonGenerator.writeNullField("country");
        }
    }

    private void writeFoundedAtField(JsonGenerator jsonGenerator, Year year) throws IOException {
        if (year != null) {
            jsonGenerator.writeStringField("foundedAt", year.toString());
        } else {
            jsonGenerator.writeNullField("foundedAt");
        }
    }

    private void writeLanguagesField(JsonGenerator jsonGenerator, List<String> languages) throws IOException {
        jsonGenerator.writeFieldName("languages");
        jsonGenerator.writeStartArray();

        if(languages != null){
            for(String lang : languages){
                jsonGenerator.writeString(lang);
            }
        }

        jsonGenerator.writeEndArray();
    }

    /**
     * Retrieves a City object from a JSON string.
     *
     * @param input The JSON string representing the City object.
     * @return The City object parsed from the JSON string.
     */
    public City getCityFromJson(String input) {
        City city = new City();
        try (JsonParser jParser = createJsonParser(input)) {
            parseJsonFields(jParser, city);
        } catch (Exception e) {

        }
        return city;
    }

    private JsonParser createJsonParser(String input) throws IOException {
        return jsonFactory.createParser(input);
    }

    private void parseJsonFields(JsonParser jParser, City city) throws IOException {
        jParser.nextToken();
        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jParser.getValueAsString();
            jParser.nextToken();

            switch (fieldName) {
                case "cityName" -> city.setCityName(getString(jParser.getText()));
                case "country" -> parseCountryField(jParser, city);
                case "cityPopulation" -> city.setCityPopulation(getInt(jParser.getText()));
                case "cityArea" -> city.setCityArea(getDouble(jParser.getText()));
                case "foundedAt" -> city.setFoundedAt(getYear(jParser.getText()));
                case "languages" -> city.setLanguages(getLanguages(jParser));
            }
        }
    }

    private void parseCountryField(JsonParser jParser, City city) throws IOException {
        Country country = Country.builder().countryName(jParser.getText()).build();
        city.setCountry(country);
    }

    private String getString(String text){
        return text.equalsIgnoreCase("null") ? null : text;
    }

    private int getInt(String text){
        try {
            return Integer.parseInt(text);
        } catch (Exception e){
            return 0;
        }
    }

    private double getDouble(String text){
        try {
            return Double.parseDouble(text);
        } catch (Exception e){
            return 0.0d;
        }
    }

    private Year getYear(String text){
        try {
            return Year.parse(text);
        } catch (Exception e){
            return null;
        }
    }

    private List<String> getLanguages(JsonParser jParser) throws IOException {
        List<String> languages = new ArrayList<>();

        while (jParser.nextToken() != JsonToken.END_ARRAY) {
            languages.add(getString(jParser.getText()));
        }

        return !languages.isEmpty() ? languages : null;
    }
}
