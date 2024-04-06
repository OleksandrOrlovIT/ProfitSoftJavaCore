package orlov.profit.soft.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    private City city;
    private Country country;

    private static final String CITY_NAME = "CITYNAME";
    private static final int CITY_POPULATION = 100;
    private static final double CITY_AREA = 100.0;
    private static final Year FOUNDED_YEAR = Year.of(2020);
    private static final List<String> LANGUAGES = List.of("first", "second", "third");

    @BeforeEach
    void setUp(){
        city = new City();
        country = new Country();
    }

    @Test
    void getSet_CityNameTest(){
        assertNull(city.getCityName());

        city.setCityName(CITY_NAME);

        assertNotNull(city.getCityName());
    }

    @Test
    void getSet_CountryTest(){
        assertNull(city.getCountry());

        city.setCountry(country);

        assertNotNull(city.getCountry());
    }

    @Test
    void getSet_CityPopulationTest(){
        assertEquals(0, city.getCityPopulation());

        city.setCityPopulation(CITY_POPULATION);

        assertEquals(CITY_POPULATION, city.getCityPopulation());
    }

    @Test
    void getSet_CityAreaTest(){
        assertEquals(0.0, city.getCityArea());

        city.setCityArea(CITY_AREA);

        assertEquals(CITY_AREA, city.getCityArea());
    }

    @Test
    void getSet_FoundedAtTest(){
        assertNull(city.getFoundedAt());

        city.setFoundedAt(FOUNDED_YEAR);

        assertNotNull(city.getFoundedAt());
    }

    @Test
    void getSet_LanguagesTest(){
        assertNull(city.getLanguages());

        city.setLanguages(LANGUAGES);

        assertNotNull(city.getLanguages());
    }

    @Test
    void equalsHashcode_SameObjectTest(){
        assertEquals(city, city);
    }

    @Test
    void equalsHashcode_nullTest(){
        assertNotEquals(city, null);
    }

    @Test
    void equalsHashcode_differentObjectTest(){
        assertNotEquals(city, new Object());
    }

    @Test
    void equalsHashcode_differentCityPopulationTest(){
        city.setCityPopulation(CITY_POPULATION);
        City comparedCity = City.builder().cityPopulation(CITY_POPULATION + 1).build();

        assertNotEquals(city, comparedCity);
        assertNotEquals(city.hashCode(), comparedCity.hashCode());
    }

    @Test
    void equalsHashcode_differentCityAreaTest(){
        city.setCityPopulation(CITY_POPULATION);
        city.setCityArea(CITY_AREA);

        City comparedCity = City.builder()
                .cityPopulation(CITY_POPULATION)
                .cityArea(CITY_AREA + 1)
                .build();

        assertNotEquals(city, comparedCity);
        assertNotEquals(city.hashCode(), comparedCity.hashCode());
    }

    @Test
    void equalsHashcode_differentCityNameTest(){
        city.setCityPopulation(CITY_POPULATION);
        city.setCityArea(CITY_AREA);
        city.setCityName(CITY_NAME);

        City comparedCity = City.builder()
                .cityPopulation(CITY_POPULATION)
                .cityArea(CITY_AREA)
                .cityName(CITY_NAME + "asd")
                .build();

        assertNotEquals(city, comparedCity);
        assertNotEquals(city.hashCode(), comparedCity.hashCode());
    }

    @Test
    void equalsHashcode_differentCountryNameTest(){
        city.setCityPopulation(CITY_POPULATION);
        city.setCityArea(CITY_AREA);
        city.setCityName(CITY_NAME);
        country.setCountryName("countryName");
        city.setCountry(country);

        City comparedCity = City.builder()
                .cityPopulation(CITY_POPULATION)
                .cityArea(CITY_AREA)
                .cityName(CITY_NAME)
                .country(new Country())
                .build();

        assertNotEquals(city, comparedCity);
        assertNotEquals(city.hashCode(), comparedCity.hashCode());
    }

    @Test
    void equalsHashcode_differentFoundedAtTest(){
        city.setCityPopulation(CITY_POPULATION);
        city.setCityArea(CITY_AREA);
        city.setCityName(CITY_NAME);
        city.setCountry(country);
        city.setFoundedAt(FOUNDED_YEAR);

        City comparedCity = City.builder()
                .cityPopulation(CITY_POPULATION)
                .cityArea(CITY_AREA)
                .cityName(CITY_NAME)
                .country(country)
                .foundedAt(FOUNDED_YEAR.plusYears(1))
                .build();

        assertNotEquals(city, comparedCity);
        assertNotEquals(city.hashCode(), comparedCity.hashCode());
    }

    @Test
    void equalsHashcode_differentLanguagesTest(){
        city.setCityPopulation(CITY_POPULATION);
        city.setCityArea(CITY_AREA);
        city.setCityName(CITY_NAME);
        city.setCountry(country);
        city.setFoundedAt(FOUNDED_YEAR);
        city.setLanguages(LANGUAGES);

        City comparedCity = City.builder()
                .cityPopulation(CITY_POPULATION)
                .cityArea(CITY_AREA)
                .cityName(CITY_NAME)
                .country(country)
                .foundedAt(FOUNDED_YEAR)
                .languages(List.of("first", "second"))
                .build();

        assertNotEquals(city, comparedCity);
        assertNotEquals(city.hashCode(), comparedCity.hashCode());
    }

    @Test
    void toStringTest(){
        city.setCityPopulation(CITY_POPULATION);
        city.setCityArea(CITY_AREA);
        city.setCityName(CITY_NAME);
        country.setCountryName("countryName");
        city.setCountry(country);
        city.setFoundedAt(FOUNDED_YEAR);
        city.setLanguages(LANGUAGES);

        String expected = "City{cityName='CITYNAME', country=countryName, cityPopulation=100, cityArea=100.0," +
                " foundedAt=2020, languages=[first, second, third]}";

        assertEquals(expected, city.toString());
    }

    @Test
    void cityBuilder_toStringTest(){
        country.setCountryName("countryName");

        City.CityBuilder cityBuilder = City.builder()
                .cityName(CITY_NAME)
                .country(country)
                .cityPopulation(CITY_POPULATION)
                .cityArea(CITY_AREA)
                .foundedAt(FOUNDED_YEAR)
                .languages(LANGUAGES);

        String expected = "City.CityBuilder(cityName=CITYNAME, country=Country{countryName='countryName'," +
                " countryArea=0.0, currency='null'}, cityPopulation=100, cityArea=100.0, foundedAt=2020," +
                " languages=[first, second, third])";

        assertEquals(expected, cityBuilder.toString());
    }
}