package orlov.profit.soft.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov.profit.soft.util.json.deserializer.CountryDeserializer;
import orlov.profit.soft.util.json.deserializer.YearDeserializer;

import java.time.Year;
import java.util.List;
import java.util.Objects;

/**
 * Represents a city entity with its properties.
 */

@Setter
@Getter
@NoArgsConstructor
public class City {

    /**
     * The name of the city.
     */
    private String cityName;

    /**
     * The country to which the city belongs.
     */
    @JsonDeserialize(using = CountryDeserializer.class)
    private Country country;

    /**
     * The population of the city.
     */
    private int cityPopulation;

    /**
     * The area of the city in kilometers.
     */
    private double cityArea;

    /**
     * The year the city was founded.
     */
    @JsonDeserialize(using = YearDeserializer.class)
    private Year foundedAt;

    /**
     * The list of languages spoken in the city.
     */
    private List<String> languages;

    /**
     * Constructs a new City object.
     * */
    @Builder
    public City(String cityName, Country country, int cityPopulation, double cityArea, Year foundedAt, List<String> languages) {
        this.cityName = cityName;
        this.country = country;
        this.cityPopulation = cityPopulation;
        this.cityArea = cityArea;
        this.foundedAt = foundedAt;
        this.languages = languages;
    }

    /**
     * Compares this City object to another object for equality.
     *
     * @param object The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        City city = (City) object;

        if (cityPopulation != city.cityPopulation) return false;
        if (Double.compare(cityArea, city.cityArea) != 0) return false;
        if (!Objects.equals(cityName, city.cityName)) return false;
        if (!Objects.equals(country != null ? country.getCountryName() : null, city.country != null ? city.country.getCountryName() : null)) return false;
        if (!Objects.equals(foundedAt, city.foundedAt)) return false;
        return Objects.equals(languages, city.languages);
    }

    /**
     * Generates a hash code for this City object.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = cityName != null ? cityName.hashCode() : 0;
        result = 31 * result + cityPopulation;
        temp = Double.doubleToLongBits(cityArea);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (foundedAt != null ? foundedAt.hashCode() : 0);
        result = 31 * result + (languages != null ? languages.hashCode() : 0);
        result = 31 * result + (country != null && country.getCountryName() != null ? country.getCountryName().hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of the City object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", country=" + (country != null ? country.getCountryName() : null) +
                ", cityPopulation=" + cityPopulation +
                ", cityArea=" + cityArea +
                ", foundedAt=" + foundedAt +
                ", languages=" + languages +
                '}';
    }
}
