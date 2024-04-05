package orlov.profit.soft;

import orlov.profit.soft.model.City;
import orlov.profit.soft.model.Country;
import orlov.profit.soft.util.json.JSONParser;

import java.io.BufferedWriter;
import java.io.File;
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


        City kiyv = City.builder()
                .cityName("Kiyv")
                .country(ukraine)
                .cityPopulation(2_887_974)
                .cityArea(839.0d)
                .foundedAt(Year.of(482))
                .languages(List.of("Ukrainian", "English"))
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

        City la = City.builder()
                .cityName("Los Angeles")
                .country(USA)
                .cityPopulation(3_971_883)
                .cityArea(1_302.0d)
                .foundedAt(Year.of(1781))
                .languages(List.of("English", "Spanish", "Chinese", "Korean"))
                .build();

        City chicago = City.builder()
                .cityName("Chicago")
                .country(USA)
                .cityPopulation(2_693_976)
                .cityArea(606.1d)
                .foundedAt(Year.of(1837))
                .languages(List.of("English", "Spanish", "Polish"))
                .build();

        String kharkivLine = checkJSONParser(kharkiv);
        String kiyvLine = checkJSONParser(kiyv);
        String nycLine = checkJSONParser(nyc);
        String laLine = checkJSONParser(la);
        String chicagoLine = checkJSONParser(chicago);

        String filePath = "src/main/resources/citiesFiveThousand.json";

        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            }
        }

        writeLineToFile("[", filePath);
        for(int i = 0; i < 100_000; i++) {
            writeLineToFile(kharkivLine + ",", filePath);
            writeLineToFile(kiyvLine + ",", filePath);
            writeLineToFile(nycLine + ",", filePath);
            writeLineToFile( laLine + ",", filePath);
            if(i == 99_999){
                writeLineToFile(chicagoLine, filePath);
            } else {
                writeLineToFile(chicagoLine + ",", filePath);
            }
        }
        writeLineToFile("]", filePath);
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