package orlov.profit.soft.util.xml;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import orlov.profit.soft.util.json.JSONParserV2;
import orlov.profit.soft.util.xml.model.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class XMLConverter {

    public static void convertMapsToXMLStats(int numberOfThreads, String inputDirectoryPath,
                                             List<String> fieldNames, String outputDirectoryPath) {
        CompletableFuture<ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>> futureMap =
                JSONParserV2.runInParallel(numberOfThreads, inputDirectoryPath, fieldNames);
        try {
            ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> result = futureMap.get();
            for (Map.Entry<String, ConcurrentHashMap<String, Integer>> entry : result.entrySet()) {
                createXMLStatsByMap(entry.getKey(), entry.getValue(), outputDirectoryPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createXMLStatsByMap(String fieldName, Map<String, Integer> map, String outputDirectoryPath) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();

        createDirectoryIfNotExists(outputDirectoryPath);

        String filePath = outputDirectoryPath + fieldName + ".xml";

        try {
            writeXMLToFile(filePath, map, xmlMapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void createDirectoryIfNotExists(String outputDirectoryPath) {
        File directory = new File(outputDirectoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("Failed to create directory: " + outputDirectoryPath);
            }
        }
    }

    private static void writeXMLToFile(String filePath, Map<String, Integer> map, XmlMapper xmlMapper) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("<statistics>\n");
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String itemXml = xmlMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(new Item(entry.getKey(), entry.getValue()));
                writer.write(itemXml);
            }
            writer.write("</statistics>");
            writer.flush();
            System.out.println("XML file has been generated at: " + filePath);
        }
    }
}