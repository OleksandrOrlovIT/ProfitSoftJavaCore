package orlov.profit.soft.util;

import orlov.profit.soft.model.City;
import orlov.profit.soft.util.json.InputStreamJsonArrayStreamDataSupplier;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainOutput {
    public static void main(String[] args) {
        // Path to your JSON file
        String filePath = "src/main/resources/cities10000.json";

        // Read JSON file as an InputStream*
        int kharkivNameCount = 0;
        for (int i = 0; i < 100; i++) {
            try (InputStream inputStream = new FileInputStream(filePath)) {
                // Create InputStreamJsonArrayStreamDataSupplier with City.class
                InputStreamJsonArrayStreamDataSupplier<City> dataSupplier
                        = new InputStreamJsonArrayStreamDataSupplier<>(City.class, inputStream);

                kharkivNameCount += (int) dataSupplier.get().filter(e -> e.getCityName().equals("Kharkiv")).count();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(kharkivNameCount);
    }
}