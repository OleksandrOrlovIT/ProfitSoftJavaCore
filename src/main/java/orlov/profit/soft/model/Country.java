package orlov.profit.soft.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


/**
 * Represents a country entity with its properties.
 */
@Setter
@Getter
@NoArgsConstructor
public class Country {

    /**
     * The name of the country.
     */
    private String countryName;

    /**
     * The area of the country.
     */
    private double countryArea;

    /**
     * The currency used in the country.
     */
    private String currency;

    /**
     * The list of cities in the country.
     */
    private List<City> cities;


    /**
     * Constructs a new Country object.
     */
    @Builder
    public Country(String countryName, double countryArea, String currency, List<City> cities) {
        this.countryName = countryName;
        this.countryArea = countryArea;
        this.currency = currency;
        this.cities = cities;
    }

    /**
     * Compares this Country object to another object for equality.
     *
     * @param object The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Country country = (Country) object;

        if (Double.compare(countryArea, country.countryArea) != 0) return false;
        if (!Objects.equals(countryName, country.countryName)) return false;
        return Objects.equals(currency, country.currency);
    }

    /**
     * Generates a hash code for this Country object.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = countryName != null ? countryName.hashCode() : 0;
        temp = Double.doubleToLongBits(countryArea);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of the Country object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", countryArea=" + countryArea +
                ", currency='" + currency + '\'' +
                '}';
    }
}
