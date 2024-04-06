package orlov.profit.soft;

import orlov.profit.soft.util.xml.XMLConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to JSON file to XML statistics application");
        System.out.println("Please input number of threads from 1 to 8: ");
        int threads = 8;
        try {
            threads = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Wrong input number of threads should be a number from 1 to 8\n" +
                    "Using default value of 8");
        } finally {
            scanner.nextLine();
        }

        System.out.println("Please input inputDirectory path with your .json files (default = src/main/resources/json): ");
        String inputDirPath = scanner.nextLine();

        System.out.println("Please input field names separated by comma to get statistics by them. " +
                "Example for all fields:\ncityName,country,cityPopulation,cityArea,foundedAt,languages");
        String line = scanner.nextLine().trim();
        List<String> fieldNames = clearWrongFieldNames(line.split(","));

        System.out.println("Please input directory path where you want to save your xml files" +
                " (default = src/main/resources/xml): ");
        String outputDirPath = scanner.nextLine();

        XMLConverter.convertMapsToXMLStats(threads, inputDirPath, fieldNames, outputDirPath);
    }

    public static List<String> clearWrongFieldNames(String[] fields) {
        List<String> validFields = new ArrayList<>();

        for (String field : fields) {
            if (isValidField(field)) {
                validFields.add(field);
            }
        }

        return validFields;
    }

    static boolean isValidField(String fieldName) {
        return fieldName.equals("cityName") ||
                fieldName.equals("country") ||
                fieldName.equals("cityPopulation") ||
                fieldName.equals("cityArea") ||
                fieldName.equals("foundedAt") ||
                fieldName.equals("languages");
    }
}