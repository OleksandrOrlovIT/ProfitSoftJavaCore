package orlov.profit.soft.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class City {

    private String cityName;

    private Country country;

    private int cityPopulation;

    //kilometers
    private double cityArea;

    private Year foundedAt;

    private List<String> languages;

    @Builder
    public City(String cityName, Country country, int cityPopulation, double cityArea, Year foundedAt, List<String> languages) {
        this.cityName = cityName;
        this.country = country;
        this.cityPopulation = cityPopulation;
        this.cityArea = cityArea;
        this.foundedAt = foundedAt;
        this.languages = languages;
    }

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
        result = 31 * result + (country != null ? country.getCountryName().hashCode() : 0);
        return result;
    }

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
