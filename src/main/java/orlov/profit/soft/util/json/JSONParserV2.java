package orlov.profit.soft.util.json;

import orlov.profit.soft.model.City;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class JSONParserV2 {

    public static CompletableFuture<ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>> runInParallel
            (int numberOfThreads, String directoryPath, List<String> fieldNames) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfThreads);

        try {
            return readCityStatsAsync(directoryPath, forkJoinPool, fieldNames);
        } finally {
            shutdownForkJoinPool(forkJoinPool);
        }
    }

    private static CompletableFuture<ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>> readCityStatsAsync(
            String directoryPath, ForkJoinPool forkJoinPool, List<String> fields) {
        List<CompletableFuture<ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>>> futures = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                futures.add(CompletableFuture.supplyAsync(() -> readCityStats(getDataSupplier(file), fields), forkJoinPool));
            }
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .reduce(new ConcurrentHashMap<>(), JSONParserV2::mergeMaps));
    }

    private static InputStreamJsonArrayStreamDataSupplier<City> getDataSupplier(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
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
            case "country" -> {
                fieldStats.get(field).merge(String.valueOf(city.getCountry().getCountryName()), 1, Integer::sum);
            }
            case "languages" -> {
                city.getLanguages().forEach(language ->
                        fieldStats.get(field).merge(language, 1, Integer::sum));
            }
        }
    }

    private static ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> mergeMaps(
            ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> map1,
            ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> map2) {
        map2.forEach((key, value) -> map1.merge(key, value, (v1, v2) -> {
            v1.forEach((k, v) -> value.merge(k, v, Integer::sum));
            return value;
        }));
        return map1;
    }
}