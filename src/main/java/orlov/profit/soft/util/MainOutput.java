package orlov.profit.soft.util;

import orlov.profit.soft.model.City;
import orlov.profit.soft.util.json.InputStreamJsonArrayStreamDataSupplier;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

public class MainOutput {
    public static void main(String[] args) {
        Instant start = Instant.now();

        String filePath = "src/main/resources/citiesFiveThousand.json";

        ForkJoinPool forkJoinPool = new ForkJoinPool(8);

        CompletableFuture<ConcurrentHashMap<String, Integer>> cityNamesFuture = CompletableFuture.supplyAsync(() -> {
            ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

            for (int i = 0; i < 20; i++) {
                try (InputStream inputStream = new FileInputStream(filePath)) {
                    InputStreamJsonArrayStreamDataSupplier<City> dataSupplier
                            = new InputStreamJsonArrayStreamDataSupplier<>(City.class, inputStream);
                    readCityNames(dataSupplier, map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return map;
        }, forkJoinPool);

        // Handling the completion of the asynchronous computation
        cityNamesFuture.thenAccept(map -> {
            System.out.println(map);
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println(timeElapsed);
        });

        // Waiting for completion
        try {
            cityNamesFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Shut down the ForkJoinPool
        forkJoinPool.shutdown();
    }

    public static void readCityNames(InputStreamJsonArrayStreamDataSupplier<City> cityStream, ConcurrentHashMap<String, Integer> map) {
        cityStream.get()
                .parallel()
                .forEach(e -> {
                    map.merge(e.getCityName(), 1, Integer::sum);
                });
    }
}