package database_files;

import java.io.File;
import java.io.IOException;

public class Database {
    public boolean isExisted() {
        File a = new File("data/cities.txt");
        File b = new File("data/countries.txt");
        File c = new File("data/flights.txt");
        return a.exists() && b.exists() && c.exists();
    }

    public void createInitialData() throws IOException {
        Countries countries = new Countries();
        Cities cities = new Cities();
        Flights flights = new Flights();
        if (!isExisted()) {
            cities.create();
            countries.create();
            flights.create();
        }
    }
}
