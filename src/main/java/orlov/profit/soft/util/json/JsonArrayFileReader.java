package orlov.profit.soft.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonArrayFileReader {
    public static void main(String[] args) {
        File file = new File("src/main/resources/cities20000.json");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read JSON array from file
            JsonNode jsonNode = objectMapper.readTree(file);
            int amountKharkiv = 0;
            int amountNYC = 0;
            // Check if the root node is an array
            if (jsonNode.isArray()) {
                // Iterate over each element in the array
                for (JsonNode element : jsonNode) {
                    // Process each JSON object
                    if(element.get("cityName").asText().equals("Kharkiv")){
                        amountKharkiv++;
                    } else if(element.get("cityName").asText().equals("New York")){
                        amountNYC++;
                    }
                }

                System.out.println("Amount kharkiv = " + amountKharkiv);
                System.out.println("Amount nyc = " + amountNYC);
            } else {
                System.err.println("Root node is not an array.");
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + file.getName());
            e.printStackTrace();
        }
    }
}