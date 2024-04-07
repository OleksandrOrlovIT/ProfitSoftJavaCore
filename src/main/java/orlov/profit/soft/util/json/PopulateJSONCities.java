package orlov.profit.soft.util.json;

import orlov.profit.soft.model.City;
import orlov.profit.soft.model.Country;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for populating JSON files with cities data.
 */
public class PopulateJSONCities {

    private static final Country ukraine = Country.builder().countryName("Ukraine").build();
    private static final Country USA = Country.builder().countryName("USA").build();
    private static final Country germany = Country.builder().countryName("Germany").build();
    private static final Country france = Country.builder().countryName("France").build();

    /**
     * Populates a JSON file with city data.
     *
     * @param filePath The path to the JSON file to populate.
     * @param times    The number of times to repeat the city data in the file.
     * @param cities   The list of cities to populate the file with.
     * @throws IOException If an I/O error occurs during file writing.
     */
    public static void populateFileWithCities(String filePath, int times, List<City> cities) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            }
        }

        List<String> lines = getStringJsons(cities);

        writeToFile(lines, times, filePath);
    }

    public static void writeToFile(List<String> lines, int times, String filePath){
        writeLineToFile("[\n", filePath);
        StringBuilder jsons = new StringBuilder();

        for(String line : lines){
            jsons.append(line).append(",").append("\n");
        }

        String s = jsons.toString();

        for(int i = 0; i < times - 1; i++) {
            writeLineToFile(s, filePath);
        }

        for(int i = 0; i < lines.size() - 1; i++){
            writeLineToFile(lines.get(i) + ",\n", filePath);
        }

        writeLineToFile(lines.get(lines.size() - 1) + "\n", filePath);

        writeLineToFile("]", filePath);
    }

    public static void writeLineToFile(String line, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getStringJsons(List<City> cities) throws IOException {
        JSONParser jsonParser = new JSONParser();

        List<String> citiesJsons = new ArrayList<>();

        for (City city : cities){
            citiesJsons.add(jsonParser.createCityJson(city));
        }

        return citiesJsons;
    }

    public static City getKiyv(){
        return City.builder()
                .cityName("Kiyv")
                .country(ukraine)
                .cityPopulation(2_887_974)
                .cityArea(839.0d)
                .foundedAt(Year.of(482))
                .languages(List.of("Ukrainian", "English"))
                .build();
    }

    public static City getKharkiv(){
        return City.builder()
                .cityName("Kharkiv")
                .country(ukraine)
                .cityPopulation(1430885)
                .cityArea(31400.0d)
                .foundedAt(Year.of(1654))
                .languages(List.of("Ukrainian", "English"))
                .build();
    }

    public static City getNYC(){
        return City.builder()
                .cityName("New York")
                .country(USA)
                .cityPopulation(8_336_000)
                .cityArea(141_297.0d)
                .foundedAt(Year.of(1624))
                .languages(List.of("English", "Spanish", "Chinese"))
                .build();
    }

    public static City getLA(){
        return City.builder()
                .cityName("Los Angeles")
                .country(USA)
                .cityPopulation(3_971_883)
                .cityArea(1_302.0d)
                .foundedAt(Year.of(1781))
                .languages(List.of("English", "Spanish", "Chinese", "Korean"))
                .build();
    }

    public static City getChicago(){
        return City.builder()
                .cityName("Chicago")
                .country(USA)
                .cityPopulation(2_693_976)
                .cityArea(606.1d)
                .foundedAt(Year.of(1837))
                .languages(List.of("English", "Spanish", "Polish"))
                .build();
    }

    public static City getBerlin(){
        return City.builder()
                .cityName("Berlin")
                .country(germany)
                .cityPopulation(3769495)
                .cityArea(891.8d)
                .foundedAt(Year.of(1237))
                .languages(List.of("German", "English"))
                .build();
    }

    public static City getMunich() {
        return City.builder()
                .cityName("Munich")
                .country(germany)
                .cityPopulation(1471508)
                .cityArea(310.4d)
                .foundedAt(Year.of(1158))
                .languages(List.of("German", "English"))
                .build();
    }

    public static City getParis(){
        return City.builder()
                .cityName("Paris")
                .country(france)
                .cityPopulation(2140526)
                .cityArea(105.4d)
                .foundedAt(Year.of(259))
                .languages(List.of("French", "English"))
                .build();
    }

    public static City getLyon(){
        return City.builder()
                .cityName("Lyon")
                .country(france)
                .cityPopulation(518635)
                .cityArea(47.87d)
                .foundedAt(Year.of(43))
                .languages(List.of("French", "English"))
                .build();
    }
}