package orlov.profit.soft.util.json;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import orlov.profit.soft.model.City;
import orlov.profit.soft.model.Country;

import java.io.IOException;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONParserTest {

    private static JSONParser jsonParser;

    private static Country ukraine;
    private static City kharkiv;
    private static City emptyCity;

    @BeforeAll
    static void setUpJSONParser(){
        jsonParser = new JSONParser();
    }

    @BeforeEach
    void setUpCityAndCountryTest(){
        ukraine = Country.builder().countryName("Ukraine").build();

        kharkiv = City.builder()
                .cityName("Kharkiv")
                .country(ukraine)
                .cityPopulation(1430885)
                .cityArea(31400.0d)
                .foundedAt(Year.of(1654))
                .languages(List.of("Ukranian", "English"))
                .build();

        emptyCity = new City();
    }

    @Test
    void createCityJson_ValidCityTest() throws IOException {
        String expected = "{\"cityName\":\"Kharkiv\",\"country\":\"Ukraine\",\"cityPopulation\":1430885," +
                "\"cityArea\":31400.0,\"foundedAt\":\"1654\",\"languages\":[\"Ukranian\",\"English\"]}";

        assertEquals(expected, jsonParser.createCityJson(kharkiv));
    }

    @Test
    void createCityJson_NullCityTest() {
        var e = assertThrows(NullPointerException.class, () -> jsonParser.createCityJson(null));
        assertEquals("City can't be null", e.getMessage());
    }

    @Test
    void createCityJson_CityEmptyFieldsTest() throws IOException {
        String expected = "{\"cityName\":null,\"country\":null,\"cityPopulation\":0," +
                "\"cityArea\":0.0,\"foundedAt\":null,\"languages\":[]}";

        assertEquals(expected, jsonParser.createCityJson(emptyCity));
    }

    @Test
    void getCityFromJson_ValidJSONTest() {
        String json = "{\"cityName\":\"Kharkiv\",\"country\":\"Ukraine\",\"cityPopulation\":1430885," +
                "\"cityArea\":31400.0,\"foundedAt\":\"1654\",\"languages\":[\"Ukranian\",\"English\"]}";

        City city = jsonParser.getCityFromJson(json);
        System.out.println(city);
        System.out.println(kharkiv);
        assertEquals(kharkiv, jsonParser.getCityFromJson(json));
    }

    @Test
    void getCityFromJson_EmptyJsonTest() {
        String json = "{}";

        assertEquals(emptyCity, jsonParser.getCityFromJson(json));
    }

    @Test
    void getCityFromJson_NullFieldsJsonTest() {
        String json = "{\"cityName\":\"null\",\"countryName\":\"null\"" +
                ",\"cityPopulation\":null,\"cityArea\":null,\"foundedAt\":\"null\",\"languages\":[]}}";

        City city2 = jsonParser.getCityFromJson(json);

        assertEquals(emptyCity, city2);
    }

    @Test
    void getCityFromJson_BrokenJsonTest() {
        String json = "\"cityName\":\"null\"[]";

        City city2 = jsonParser.getCityFromJson(json);

        assertEquals(emptyCity, city2);
    }

    @Test
    void getCityFromJson_BrokenJson_BrokenArrayTest() {
        String json = "{\"cityName\":\"Kharkiv\",\"countryName\":\"Ukraine\",\"cityPopulation\":1430885," +
                "\"cityArea\":31400.0,\"foundedAt\":\"1654\",\"languages\":[\"Ukranian\",";


        City city2 = jsonParser.getCityFromJson(json);

        assertNull(city2.getLanguages());
    }
}