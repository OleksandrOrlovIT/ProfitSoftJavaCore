package orlov.profit.soft;

import orlov.profit.soft.model.City;
import orlov.profit.soft.model.Country;
import orlov.profit.soft.util.json.JSONParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) throws IOException {
        Country ukraine = Country.builder().countryName("Ukraine").build();

        City kharkiv = City.builder()
                .cityName("Kharkiv")
                .country(ukraine)
                .cityPopulation(1430885)
                .cityArea(31400.0d)
                .foundedAt(Year.of(1654))
                .languages(List.of("Ukranian", "English"))
                .build();

        Country USA = Country.builder().countryName("USA").build();

        City nyc = City.builder()
                .cityName("New York")
                .country(USA)
                .cityPopulation(8_336_000)
                .cityArea(141_297.0d)
                .foundedAt(Year.of(1624))
                .languages(List.of("English", "Spanish", "Chinese"))
                .build();

        String kharkivLine = checkJSONParser(kharkiv);
        String nycLine = checkJSONParser(nyc);

        writeLineToFile("[", "src/main/resources/cities10000.json");
        for(int i = 0; i < 10_000; i++) {
            writeLineToFile(kharkivLine + ",", "src/main/resources/cities10000.json");
            writeLineToFile(nycLine + ",", "src/main/resources/cities10000.json");
        }
        writeLineToFile("]", "src/main/resources/cities10000.json");
    }

    public static String checkJSONParser(City city) throws IOException {
        JSONParser jsonParser = new JSONParser();
        return jsonParser.createCityJson(city);
    }

    public static void writeLineToFile(String line, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}