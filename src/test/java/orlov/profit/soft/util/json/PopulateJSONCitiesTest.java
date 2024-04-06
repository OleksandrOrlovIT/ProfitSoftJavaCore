package orlov.profit.soft.util.json;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import orlov.profit.soft.model.City;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PopulateJSONCitiesTest {
    private static final String resourcesPath = "src/test/resources/";
    private static final JSONParser jsonParser = new JSONParser();

    @BeforeAll
    static void setResourcesPath() {
        String directoryPath = resourcesPath;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Directory created successfully.");
            } else {
                throw new RuntimeException("Failed to create directory.");
            }
        } else {
            System.out.println("Directory already exists.");
        }
    }

    @Test
    void populateFileWith5CitiesTest() throws IOException {
        String filePath = resourcesPath + "test.json";
        int times = 1;
        List<City> cities = new ArrayList<>();
        cities.add(PopulateJSONCities.getKiyv());
        cities.add(PopulateJSONCities.getKharkiv());
        cities.add(PopulateJSONCities.getNYC());
        cities.add(PopulateJSONCities.getLA());
        cities.add(PopulateJSONCities.getChicago());

        PopulateJSONCities.populateFileWithCities(filePath, times, cities);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        assertEquals("[", lines.get(0));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getKiyv()) + ",", lines.get(1));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getKharkiv()) + ",", lines.get(2));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getNYC()) + ",", lines.get(3));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getLA()) + ",", lines.get(4));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getChicago()), lines.get(5));
        assertEquals("]", lines.get(6));
    }

    @Test
    void populateFileWith1CityTest_times5() throws IOException {
        String filePath = resourcesPath + "test.json";
        int times = 5;
        List<City> cities = new ArrayList<>();
        cities.add(PopulateJSONCities.getBerlin());

        PopulateJSONCities.populateFileWithCities(filePath, times, cities);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        assertEquals("[", lines.get(0));
        for(int i = 1; i < 5; i++){
            assertEquals(jsonParser.createCityJson(PopulateJSONCities.getBerlin()) + ",", lines.get(i));
        }
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getBerlin()), lines.get(5));
        assertEquals("]", lines.get(6));
    }

    @Test
    void populateFileWith3CityTest_times2() throws IOException {
        String filePath = resourcesPath + "test.json";
        int times = 2;
        List<City> cities = new ArrayList<>();
        cities.add(PopulateJSONCities.getMunich());
        cities.add(PopulateJSONCities.getParis());
        cities.add(PopulateJSONCities.getLyon());

        PopulateJSONCities.populateFileWithCities(filePath, times, cities);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        assertEquals("[", lines.get(0));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getMunich()) + ",", lines.get(1));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getParis()) + ",", lines.get(2));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getLyon()) + ",", lines.get(3));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getMunich()) + ",", lines.get(4));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getParis()) + ",", lines.get(5));
        assertEquals(jsonParser.createCityJson(PopulateJSONCities.getLyon()), lines.get(6));
        assertEquals("]", lines.get(7));
    }

    @AfterAll
    static void deleteFiles() {
        deleteDirectory(new File(resourcesPath));
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
}