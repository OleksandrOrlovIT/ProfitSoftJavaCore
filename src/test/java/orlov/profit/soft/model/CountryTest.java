package orlov.profit.soft.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private Country country;

    private static final String COUNTRY_NAME = "COUNTRY_NAME";
    private static final double COUNTRY_AREA = 100.0;
    private static final String CURRENCY = "Currency";

    @BeforeEach
    void setUp() {
        country = new Country();
    }

    @Test
    void equals_SameObjectTest(){
        assertEquals(country, country);
    }

    @Test
    void equals_NullObjectTest(){
        assertNotEquals(country, null);
    }

    @Test
    void equals_DifferentObjectTest(){
        assertNotEquals(country, new Object());
    }

    @Test
    void equalsHashCode_CountryAreaTest(){
        country.setCountryArea(COUNTRY_AREA);

        Country someCountry = Country.builder().build();

        assertNotEquals(country, someCountry);
        assertNotEquals(country.hashCode(), someCountry.hashCode());
    }

    @Test
    void equalsHashCode_CountryNameTest(){
        country.setCountryArea(COUNTRY_AREA);
        country.setCountryName(COUNTRY_NAME);

        Country someCountry = Country.builder()
                .countryArea(COUNTRY_AREA)
                .build();

        assertNotEquals(country, someCountry);
        assertNotEquals(country.hashCode(), someCountry.hashCode());
    }

    @Test
    void equalsHashCode_CountryCurrencyTest(){
        country.setCountryArea(COUNTRY_AREA);
        country.setCountryName(COUNTRY_NAME);
        country.setCurrency(CURRENCY);

        Country someCountry = Country.builder()
                .countryArea(COUNTRY_AREA)
                .countryName(COUNTRY_NAME)
                .build();

        assertNotEquals(country, someCountry);
        assertNotEquals(country.hashCode(), someCountry.hashCode());
        assertEquals(country.getCountryArea(), someCountry.getCountryArea());
        assertEquals(country.getCountryName(), someCountry.getCountryName());
        assertNotEquals(country.getCurrency(), someCountry.getCurrency());
        assertEquals(country.getCities(), someCountry.getCities());
    }

    @Test
    void toString_Test(){
        country.setCountryArea(COUNTRY_AREA);
        country.setCountryName(COUNTRY_NAME);
        country.setCurrency(CURRENCY);
        country.setCities(List.of(new City()));

        String expected = "Country{countryName='COUNTRY_NAME', countryArea=100.0, currency='Currency'}";
        assertEquals(expected, country.toString());
    }

    @Test
    void countryBuilder_Test(){
        Country.CountryBuilder countryBuilder = Country.builder()
                .countryName(COUNTRY_NAME)
                .countryArea(COUNTRY_AREA)
                .currency(CURRENCY)
                .cities(List.of(new City()));

        String expected = "Country.CountryBuilder(countryName=COUNTRY_NAME, countryArea=100.0, currency=Currency," +
                " cities=[City{cityName='null', country=null, cityPopulation=0, cityArea=0.0, foundedAt=null," +
                " languages=null}])";

        assertEquals(expected, countryBuilder.toString());
    }
}