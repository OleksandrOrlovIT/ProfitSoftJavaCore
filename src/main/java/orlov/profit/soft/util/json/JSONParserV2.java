package orlov.profit.soft.util.json;

import orlov.profit.soft.model.City;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class JSONParserV2 {

    public static void main(String[] args) {
        String filePath = "src/main/resources/cities.json";
        runInParallel(8, filePath);
    }

    public static void runInParallel(int numberOfThreads, String filePath){
        ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfThreads);

        try {
            processResult(readCityStatsAsync(filePath, forkJoinPool,
                    List.of("cityName", "cityPopulation", "cityArea", "foundedAt", "countryName", "languages")));

        } finally {
            shutdownForkJoinPool(forkJoinPool);
        }
    }

    private static CompletableFuture<ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>> readCityStatsAsync(
            String filePath, ForkJoinPool forkJoinPool, List<String> fields) {
        return CompletableFuture.supplyAsync(() -> readCityStats(getDataSupplier(filePath), fields), forkJoinPool);
    }

    private static InputStreamJsonArrayStreamDataSupplier<City> getDataSupplier(String filepath) {
        try {
            InputStream inputStream = new FileInputStream(filepath);
            return new InputStreamJsonArrayStreamDataSupplier<>(City.class, inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading city names", e);
        }
    }

    private static void processResult(
            CompletableFuture<ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>> cityNamesFuture) {
        try {
            ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> map = cityNamesFuture.get();
            for (Map.Entry<String, ConcurrentHashMap<String, Integer>> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void shutdownForkJoinPool(ForkJoinPool forkJoinPool) {
        forkJoinPool.shutdown();
    }

    public static ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> readCityStats(
            InputStreamJsonArrayStreamDataSupplier<City> cityStream,
            List<String> fieldsToCheck) {

        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> fieldStats = new ConcurrentHashMap<>();

        fieldsToCheck.forEach(field -> fieldStats.put(field, new ConcurrentHashMap<>()));

        cityStream.get()
                .parallel()
                .forEach(city -> {
                    fieldsToCheck.forEach(field -> {
                        addToFieldStats(field, city, fieldStats);
                    });
                });

        return fieldStats;
    }

    private static void addToFieldStats(String field, City city, ConcurrentHashMap<String,
            ConcurrentHashMap<String, Integer>> fieldStats) {
        switch (field) {
            case "cityName" -> {
                fieldStats.get(field).merge(city.getCityName(), 1, Integer::sum);
            }
            case "cityPopulation" -> {
                fieldStats.get(field).merge(String.valueOf(city.getCityPopulation()), 1, Integer::sum);
            }
            case "cityArea" -> {
                fieldStats.get(field).merge(String.valueOf(city.getCityArea()), 1, Integer::sum);
            }
            case "foundedAt" -> {
                fieldStats.get(field).merge(String.valueOf(city.getFoundedAt()), 1, Integer::sum);
            }
            case "countryName" -> {
                fieldStats.get(field).merge(String.valueOf(city.getCountry().getCountryName()), 1, Integer::sum);
            }
            case "languages" -> {
                city.getLanguages().forEach(language ->
                        fieldStats.get(field).merge(language, 1, Integer::sum));
            }
        }
    }
}
