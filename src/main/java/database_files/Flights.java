package database_files;

import entity.Flight;
import io.IOFlight;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Flights {
    private final IOFlight ioFlight;
    private final Cities cities = new Cities();
    private final LocalDateTime cur = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
    Flight flight1 = new Flight(1, cities.city1, cities.city2, 50, 50, cur);
    Flight flight2 = new Flight(2, cities.city2, cities.city3, 50, 50, cur);
    Flight flight3 = new Flight(3, cities.city3, cities.city4, 50, 50, cur);
    Flight flight4 = new Flight(4, cities.city4, cities.city5, 50, 50, cur);
    Flight flight5 = new Flight(5, cities.city5, cities.city6, 50, 50, cur);
    Flight flight6 = new Flight(6, cities.city6, cities.city7, 50, 50, cur);
    Flight flight7 = new Flight(7, cities.city7, cities.city8, 50, 50, cur);
    Flight flight8 = new Flight(8, cities.city8, cities.city9, 50, 50, cur);
    Flight flight9 = new Flight(9, cities.city9, cities.city10, 50, 50, cur);
    Flight flight10 = new Flight(10, cities.city10, cities.city11, 50, 50, cur);
    Flight flight11 = new Flight(11, cities.city11, cities.city12, 50, 50, cur);
    Flight flight12 = new Flight(12, cities.city12, cities.city13, 50, 50, cur);
    Flight flight13 = new Flight(13, cities.city13, cities.city14, 50, 50, cur);
    Flight flight14 = new Flight(14, cities.city14, cities.city15, 50, 50, cur);
    Flight flight15 = new Flight(15, cities.city15, cities.city16, 50, 50, cur);
    Flight flight16 = new Flight(16, cities.city16, cities.city17, 50, 50, cur);
    Flight flight17 = new Flight(17, cities.city17, cities.city18, 50, 50, cur);
    Flight flight18 = new Flight(18, cities.city18, cities.city19, 50, 50, cur);
    Flight flight19 = new Flight(19, cities.city19, cities.city20, 50, 50, cur);
    Flight flight20 = new Flight(20, cities.city20, cities.city1, 50, 50, cur);
    private final List<Flight> data = Arrays.asList(flight1, flight2, flight3, flight4, flight5,
            flight6, flight7, flight8, flight9, flight10,
            flight11, flight12, flight13, flight14, flight15,
            flight16, flight17, flight18, flight19, flight20);


    public Flights() {
        this.ioFlight = new IOFlight();
    }

    public void create() throws IOException {
        List<Flight> flights = new ArrayList<>();
        int k = 1;
        for (int n = 0; n < 15; n++) {
            for (Flight f : data) {
                flights.add(new Flight(k++, f.getSource(), f.getDestination(), f.getSeats(), f.getEmptySeats(), f.getDate().plusHours((int) (Math.random() * 100))));
                flights.add(new Flight(k++, f.getSource(), f.getDestination(), f.getSeats(), f.getEmptySeats(), f.getDate().plusDays((int) (Math.random() * 50))));
                flights.add(new Flight(k++, f.getSource(), f.getDestination(), f.getSeats(), f.getEmptySeats(), f.getDate().plusMonths((int) (Math.random() * 15))));
                flights.add(new Flight(k++, f.getSource(), f.getDestination(), f.getSeats(), f.getEmptySeats(), f.getDate().plusYears((int) (Math.random() * 8))));
            }
        }

        ioFlight.write(flights);
    }
}
