package orlov.profit.soft.util.xml;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class XMLConverterTest {

    @Test
    public void testCreateXMLStatsByMapTest() throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir") + File.separator + "testDir1" + File.separator;
        File directory = new File(tempDir);
        directory.mkdirs();

        Map<String, Integer> sampleMap = new HashMap<>();
        sampleMap.put("A", 10);
        sampleMap.put("B", 20);

        XMLConverter.createXMLStatsByMap("test", sampleMap, tempDir);

        File xmlFile = new File(tempDir + "test.xml");
        assertTrue(xmlFile.exists());

        deleteDirectory(directory);
    }

    @Test
    public void testFailedDirectoryCreationInCreateDirectoryIfNotExistsTest() {
        String invalidDirPath = "/invalid/directory/";

        XMLConverter.createDirectoryIfNotExists(invalidDirPath);

        File directory = new File(invalidDirPath);
        assertFalse(directory.exists());
    }

    @Test
    public void testConvertMapsToXMLStats() {
        String inputDirectoryPath = createTestInputDirectory();
        List<String> fieldNames = Arrays.asList("cityName", "country", "cityPopulation",
                "cityArea", "foundedAt", "languages");
        String outputDirectoryPath = createTemporaryOutputDirectory();

        XMLConverter.convertMapsToXMLStats(8, inputDirectoryPath, fieldNames, outputDirectoryPath);

        File outputDirectory = new File(outputDirectoryPath);
        assertTrue(outputDirectory.exists() && outputDirectory.isDirectory());

        File[] xmlFiles = outputDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertEquals(6, Objects.requireNonNull(xmlFiles).length);

        deleteDirectory(new File(inputDirectoryPath));
        deleteDirectory(outputDirectory);
    }

    private String createTestInputDirectory() {
        String inputDirectoryPath = System.getProperty("java.io.tmpdir") + File.separator + "testInputDir2" + File.separator;
        File inputDirectory = new File(inputDirectoryPath);
        inputDirectory.mkdirs();

        String jsonFilePath = inputDirectoryPath + "testData.json";
        String jsonData = "[\n" +
                "  {\"cityName\":\"Kharkiv\",\"country\":\"Ukraine\",\"cityPopulation\":1430885,\"cityArea\":31400.0,\"foundedAt\":\"1654\",\"languages\":[\"Ukranian\",\"English\"]},\n" +
                "  {\"cityName\":\"New York\",\"country\":\"USA\",\"cityPopulation\":8336000,\"cityArea\":141297.0,\"foundedAt\":\"1624\",\"languages\":[\"English\",\"Spanish\",\"Chinese\"]}\n" +
                "]";

        try (FileWriter writer = new FileWriter(jsonFilePath)) {
            writer.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputDirectoryPath;
    }

    private String createTemporaryOutputDirectory() {
        String outputDirectoryPath = System.getProperty("java.io.tmpdir") + File.separator + "testOutputDir" + File.separator;
        File outputDirectory = new File(outputDirectoryPath);
        outputDirectory.mkdirs();
        return outputDirectoryPath;
    }

    private void deleteDirectory(File directory) {
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