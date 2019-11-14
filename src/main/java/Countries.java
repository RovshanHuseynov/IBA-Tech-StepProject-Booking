import entity.Country;
import io.IOCountry;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Countries implements Iterable<Country> {
    Country country1 = new Country(1, "Norway");
    Country country2 = new Country(2, "Switzerland");
    Country country3 = new Country(3, "Australia");
    Country country4 = new Country(4, "Ireland");
    Country country5 = new Country(5, "Germany");
    Country country6 = new Country(6, "Iceland");
    Country country7 = new Country(7, "China");
    Country country8 = new Country(8, "Sweden");
    Country country9 = new Country(9, "Denmark");
    Country country10 = new Country(10, "Canada");
    Country country11 = new Country(11, "United States");
    Country country12 = new Country(12, "United Kingdom");
    Country country13 = new Country(13, "Finland");
    Country country14 = new Country(14, "Italy");
    Country country15 = new Country(15, "Belgium");
    Country country16 = new Country(16, "Japan");
    Country country17 = new Country(17, "Austria");
    Country country18 = new Country(18, "France");
    Country country19 = new Country(19, "Estonia");
    Country country20 = new Country(20, "Cyprus");

    private final List<Country> data = Arrays.asList(country1, country2, country3, country4, country5,
            country6, country7, country8, country9, country10,
            country11, country12, country13, country14, country15,
            country16, country17, country18, country19, country20
    );

    @Override
    public Iterator<Country> iterator() {
        return data.iterator();
    }

    public void create() {
        IOCountry ioCountry = new IOCountry();
        data.forEach(country -> {
            try {
                ioCountry.write(country);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
