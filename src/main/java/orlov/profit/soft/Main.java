package orlov.profit.soft;

import orlov.profit.soft.model.City;
import orlov.profit.soft.model.Country;
import orlov.profit.soft.util.json.JSONParser;

import java.io.IOException;
import java.time.Year;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Country country = Country.builder().countryName("Ukraine").build();

        City city = City.builder()
                .cityName("Kharkiv")
                .country(country)
                .cityPopulation(1430885)
                .cityArea(31400.0d)
                .foundedAt(Year.of(1654))
                .languages(List.of("Ukranian", "English"))
                .build();

        checkJSONParser(city);
        checkJSONParser(city);
    }

    public static void checkJSONParser(City city) throws IOException {
        JSONParser jsonParser = new JSONParser();
        System.out.println(jsonParser.createCityJson(city));

        System.out.println(jsonParser.createCityJson(city));
    }
}