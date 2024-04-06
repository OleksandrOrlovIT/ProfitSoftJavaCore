package speedTest;

import org.junit.jupiter.api.*;
import orlov.profit.soft.model.City;
import orlov.profit.soft.util.json.PopulateJSONCities;
import orlov.profit.soft.util.xml.XMLConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpeedTest {

    private static final String INPUT_DIR
            = System.getProperty("java.io.tmpdir") + File.separator + "input" + File.separator;

    private static final String OUTPUT_DIR
            = System.getProperty("java.io.tmpdir") + File.separator + "output" + File.separator;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        File inputDir = new File(INPUT_DIR);
        if (!inputDir.exists()) {
            inputDir.mkdirs();
        }

        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        List<City> cities = generateCities();

        populateFileWithCities(INPUT_DIR + "file1.json", 100, cities);
        populateFileWithCities(INPUT_DIR + "file2.json", 1000, cities);
        populateFileWithCities(INPUT_DIR + "file3.json", 10000, cities);
    }

    private static List<City> generateCities() {
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            cities.add(PopulateJSONCities.getKiyv());
            cities.add(PopulateJSONCities.getKharkiv());
            cities.add(PopulateJSONCities.getNYC());
            cities.add(PopulateJSONCities.getLA());
            cities.add(PopulateJSONCities.getChicago());
            cities.add(PopulateJSONCities.getBerlin());
            cities.add(PopulateJSONCities.getMunich());
            cities.add(PopulateJSONCities.getParis());
            cities.add(PopulateJSONCities.getLyon());
        }
        return cities;
    }

    private static void populateFileWithCities(String filePath, int times, List<City> cities) throws IOException {
        PopulateJSONCities.populateFileWithCities(filePath, times, cities);
    }

    @Test
    @Order(1)
    void assertJSONFilesCreated() {
        File file1 = new File(INPUT_DIR + "file1.json");
        File file2 = new File(INPUT_DIR + "file2.json");
        File file3 = new File(INPUT_DIR + "file3.json");

        assertTrue(file1.exists() && file1.isFile());
        assertTrue(file2.exists() && file2.isFile());
        assertTrue(file3.exists() && file3.isFile());
    }

    @Test
    @Order(2)
    void testConvertMapsToXMLStatsWithOneThread() {
        for(int threads = 1; threads <= 8; threads *= 2) {
            List<String> fieldNames = Arrays.asList("cityName", "country", "cityPopulation", "cityArea", "foundedAt", "languages");
            long startTime = System.nanoTime();

            XMLConverter.convertMapsToXMLStats(1, INPUT_DIR, fieldNames, OUTPUT_DIR);

            long endTime = System.nanoTime();
            long durationInMillis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            System.out.println("Time spent with " + threads + " threads: " + durationInMillis + " ms");

            assertXMLFilesCreated(OUTPUT_DIR);
            deleteAndCreateOutputDirectory();
        }
    }

    private void assertXMLFilesCreated(String outputDirectoryPath) {
        File outputDirectory = new File(outputDirectoryPath);
        assertTrue(outputDirectory.exists() && outputDirectory.isDirectory());

        File[] xmlFiles = outputDirectory.listFiles((dir, name) -> name.endsWith(".xml"));
        assertTrue(xmlFiles != null && xmlFiles.length > 0);
    }

    @AfterAll
    public static void cleanUp() {
        deleteDirectory(new File(INPUT_DIR));
        deleteDirectory(new File(OUTPUT_DIR));
    }

    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }

    private static void deleteAndCreateOutputDirectory(){
        deleteDirectory(new File(OUTPUT_DIR));

        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
    }
}
