package orlov.profit.soft.util.json;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONParserV2Test {

    @Test
    public void testRunInParallel() throws Exception {
        String directoryPath = createTestInputDirectory();
        List<String> fieldNames = Arrays.asList("cityName", "country");

        CompletableFuture<ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>> futureMap =
                JSONParserV2.runInParallel(4, directoryPath, fieldNames);

        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> result = futureMap.get();

        assertTrue(result.containsKey("cityName"));
        assertTrue(result.containsKey("country"));
        assertTrue(result.get("cityName").containsKey("Kharkiv"));
        assertTrue(result.get("country").containsKey("Ukraine"));
        assertTrue(result.get("cityName").containsKey("New York"));
        assertTrue(result.get("country").containsKey("USA"));

        deleteDirectory(new File(directoryPath));
    }

    private String createTestInputDirectory() {
        String directoryPath = System.getProperty("java.io.tmpdir") + File.separator + "testInputDir" + File.separator;
        File directory = new File(directoryPath);
        directory.mkdirs();

        String jsonFilePath = directoryPath + "testData.json";
        String jsonData = "[\n" +
                "  {\"cityName\":\"Kharkiv\",\"country\":\"Ukraine\",\"cityPopulation\":1430885,\"cityArea\":31400.0,\"foundedAt\":\"1654\",\"languages\":[\"Ukranian\",\"English\"]},\n" +
                "  {\"cityName\":\"New York\",\"country\":\"USA\",\"cityPopulation\":8336000,\"cityArea\":141297.0,\"foundedAt\":\"1624\",\"languages\":[\"English\",\"Spanish\",\"Chinese\"]}\n" +
                "]";

        try (FileWriter writer = new FileWriter(jsonFilePath)) {
            writer.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return directoryPath;
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
