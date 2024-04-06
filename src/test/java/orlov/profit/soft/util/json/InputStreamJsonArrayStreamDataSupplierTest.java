package orlov.profit.soft.util.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.exceptions.base.MockitoException;
import orlov.profit.soft.model.City;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputStreamJsonArrayStreamDataSupplierTest {

    @Test
    public void getTest() throws IOException {
        String jsonArrayString = "[" +
                "{\"cityName\":\"Berlin\",\"country\":\"Germany\",\"cityPopulation\":3769495,\"cityArea\":891.8,\"foundedAt\":\"1237\",\"languages\":[\"German\",\"English\"]}," +
                "{\"cityName\":\"Munich\",\"country\":\"Germany\",\"cityPopulation\":1471508,\"cityArea\":310.4,\"foundedAt\":\"1158\",\"languages\":[\"German\",\"English\"]}" +
                "]";

        InputStream inputStream = new ByteArrayInputStream(jsonArrayString.getBytes(StandardCharsets.UTF_8));

        InputStreamJsonArrayStreamDataSupplier<City> supplierMock = mock(InputStreamJsonArrayStreamDataSupplier.class);

        when(supplierMock.get()).thenReturn(new InputStreamJsonArrayStreamDataSupplier<>(City.class, inputStream).get());

        // Test the get() method
        List<City> cities = supplierMock.get().toList();

        // Assert the results
        assertEquals(2, cities.size());
        assertEquals("Berlin", cities.get(0).getCityName());
        assertEquals("Germany", cities.get(0).getCountry().getCountryName());
        assertEquals(3769495, cities.get(0).getCityPopulation());
        assertEquals(891.8, cities.get(0).getCityArea());
        assertEquals("1237", cities.get(0).getFoundedAt().toString());
        assertEquals(List.of("German", "English"), cities.get(0).getLanguages());

        assertEquals("Munich", cities.get(1).getCityName());
        assertEquals("Germany", cities.get(1).getCountry().getCountryName());
        assertEquals(1471508, cities.get(1).getCityPopulation());
        assertEquals(310.4, cities.get(1).getCityArea());
        assertEquals("1158", cities.get(1).getFoundedAt().toString());
        assertEquals(List.of("German", "English"), cities.get(1).getLanguages());
    }

    @Test
    public void emptyArrayTest() throws IOException {
        String emptyArrayString = "[]";

        InputStream inputStream = new ByteArrayInputStream(emptyArrayString.getBytes(StandardCharsets.UTF_8));

        var e = assertThrows(RuntimeException.class,
                () -> new InputStreamJsonArrayStreamDataSupplier<>(City.class, inputStream));

        String expected = "Can't get any JSON object from input " + inputStream;

        assertEquals(expected, e.getMessage());
    }

    @Test
    public void closeStreamTest() throws IOException {
        String jsonArrayString = "[" +
                "{\"cityName\":\"Berlin\",\"country\":\"Germany\",\"cityPopulation\":3769495,\"cityArea\":891.8,\"foundedAt\":\"1237\",\"languages\":[\"German\",\"English\"]}," +
                "{\"cityName\":\"Munich\",\"country\":\"Germany\",\"cityPopulation\":1471508,\"cityArea\":310.4,\"foundedAt\":\"1158\",\"languages\":[\"German\",\"English\"]}" +
                "]";

        InputStream inputStream = new ByteArrayInputStream(jsonArrayString.getBytes(StandardCharsets.UTF_8));

        InputStreamJsonArrayStreamDataSupplier<City> supplier = new InputStreamJsonArrayStreamDataSupplier<>(City.class, inputStream);

        supplier.get().close();

        assertEquals(-1, inputStream.read());
    }

    @Test
    public void jsonWithoutArrayTest() {
        String invalidJsonString = "{\"cityName\":\"Berlin\",\"country\":\"Germany\"}";

        InputStream inputStream = new ByteArrayInputStream(invalidJsonString.getBytes(StandardCharsets.UTF_8));

        assertDoesNotThrow(() -> new InputStreamJsonArrayStreamDataSupplier<>(City.class, inputStream));
    }
}