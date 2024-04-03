package orlov.profit.soft.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class Country {

    private String countryName;

    private double countryArea;

    private String currency;

    private List<City> cities;

    @Builder
    public Country(String countryName, double countryArea, String currency, List<City> cities) {
        this.countryName = countryName;
        this.countryArea = countryArea;
        this.currency = currency;
        this.cities = cities;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Country country = (Country) object;

        if (Double.compare(countryArea, country.countryArea) != 0) return false;
        if (!Objects.equals(countryName, country.countryName)) return false;
        return Objects.equals(currency, country.currency);
    }

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

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", countryArea=" + countryArea +
                ", currency='" + currency + '\'' +
                '}';
    }
}
