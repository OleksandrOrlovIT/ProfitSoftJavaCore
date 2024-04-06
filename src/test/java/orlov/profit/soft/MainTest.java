package orlov.profit.soft;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testMain() {
        String simulatedUserInput = """
                4
                /path/to/inputDirectory
                cityName,country
                /path/to/outputDirectory
                """;
        InputStream inputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        Main.main(new String[]{});

        System.setIn(System.in);
        System.setOut(System.out);

        // Verify output
        String expectedOutput = """
                Welcome to JSON file to XML statistics application
                Please input number of threads from 1 to 8:\s
                Please input inputDirectory path with your .json files (default = src/main/resources/json):\s
                Please input field names separated by comma to get statistics by them. Example for all fields:
                cityName,country,cityPopulation,cityArea,foundedAt,languages
                Please input directory path where you want to save your xml files (default = src/main/resources/xml):\s
                """;
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    public void testClearWrongFieldNames() {
        String[] fields = {"cityName", "population", "country", "invalid"};

        List<String> validFields = Main.clearWrongFieldNames(fields);

        List<String> expectedFields = Arrays.asList("cityName", "country");
        assertEquals(expectedFields, validFields);
    }

    @Test
    public void testIsValidField() {
        boolean isValid1 = Main.isValidField("cityName");
        boolean isValid2 = Main.isValidField("invalid");

        assertTrue(isValid1);
        assertFalse(isValid2);
    }
}