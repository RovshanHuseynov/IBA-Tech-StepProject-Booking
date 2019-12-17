package dao;

import entity.Flight;
import io.IOFlight;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DAOFlight implements DAO<Flight> {
    private final IOFlight ioFlight;
    private List<Flight> flights;

    public DAOFlight() throws IOException, ParseException {
        this.flights = new IOFlight().read();
        this.ioFlight = new IOFlight();
    }

    public Flight get(int flightID) {
        for (Flight flight : flights) {
            if (flightID == flight.getId()) {
                return flight;
            }
        }
        throw new IllegalArgumentException("No Flight at this id");
    }

    public List<Flight> getAll() {
        return flights;
    }

    public void put(Flight flight) {
        flights.add(flight);
    }

    public void delete(int id) {
        flights.remove(flights.get(id));
    }

    public void update(Flight flightUpdated) throws IOException {
        int indexOfUpdatedFlight = -1, cnt = -1;
        for(Flight flight : flights){
            cnt++;
            if(flight.getId() == flightUpdated.getId()){
                indexOfUpdatedFlight = cnt;
            }
        }

        flights.set(indexOfUpdatedFlight, flightUpdated);
        ioFlight.updateFile(flights);
    }

    public int size() {
        return flights.size();
    }
}