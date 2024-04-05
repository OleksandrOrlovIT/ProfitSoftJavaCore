package orlov.profit.soft.util.json;

import orlov.profit.soft.model.City;
import orlov.profit.soft.util.json.InputStreamJsonArrayStreamDataSupplier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        String filePath = "src/main/resources/cities.json"; // Update with your file path
        // Initialize input stream
        try (InputStream inputStream = new FileInputStream(filePath)) {
            // Create a thread pool with 4 threads
            ExecutorService executorService = Executors.newFixedThreadPool(4);

            // Create a shared map for storing city counts
            Map<String, Integer> cityCounts = new HashMap<>();

            // Read the entire stream into a list
            InputStreamJsonArrayStreamDataSupplier<City> dataSupplier =
                    new InputStreamJsonArrayStreamDataSupplier<>(City.class, inputStream);
            Stream<City> cityStream = dataSupplier.get();
            List<City> cityList = cityStream.collect(Collectors.toList());

            // Partition the list and submit tasks to the executor
            int chunkSize = cityList.size() / 4;
            for (int i = 0; i < 4; i++) {
                List<City> chunk = cityList.subList(i * chunkSize, Math.min((i + 1) * chunkSize, cityList.size()));
                executorService.submit(() -> processData(chunk, cityCounts));
            }

            // Shutdown the executor
            executorService.shutdown();

            // Wait for all tasks to complete
            while (!executorService.isTerminated()) {
                Thread.sleep(100); // Sleep briefly before checking again
            }

            // Print the city counts
            System.out.println("City Counts:");
            cityCounts.forEach((cityName, count) ->
                    System.out.println(cityName + ": " + count));
        }
    }

    private static void processData(List<City> cityList, Map<String, Integer> cityCounts) {
        // Process each city in the chunk and update the city counts map
        cityList.forEach(city -> {
            synchronized (cityCounts) { // Synchronize access to the shared map
                cityCounts.put(city.getCityName(),
                        cityCounts.getOrDefault(city.getCityName(), 0) + 1);
            }
        });
    }
}
