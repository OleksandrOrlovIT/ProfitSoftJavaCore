package orlov.profit.soft;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class City {

    private String cityName;

    private int cityPopulation;

    private double cityArea;

    private LocalDate foundedAt;

    private Country country;

    @Builder
    public City(String cityName, int cityPopulation, double cityArea, LocalDate foundedAt, Country country) {
        this.cityName = cityName;
        this.cityPopulation = cityPopulation;
        this.cityArea = cityArea;
        this.foundedAt = foundedAt;
        this.country = country;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        City city = (City) object;

        if (cityPopulation != city.cityPopulation) return false;
        if (Double.compare(cityArea, city.cityArea) != 0) return false;
        if (!Objects.equals(cityName, city.cityName)) return false;
        return Objects.equals(foundedAt, city.foundedAt);
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
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", cityPopulation=" + cityPopulation +
                ", cityArea=" + cityArea +
                ", foundedAt=" + foundedAt +
                ", country=" + country.getCountryName() +
                '}';
    }
}
