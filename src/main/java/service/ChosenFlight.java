package service;

import entity.Flight;

import java.util.HashMap;
import java.util.Objects;

public class ChosenFlight extends HashMap<Integer, Flight> {
    private int numOfTickets;
    private Flight flight;

    public ChosenFlight(int n, Flight flight) {
        this.numOfTickets = n;
        this.flight = flight;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public void setNumOfTickets(int numOfTickets) {
        this.numOfTickets = numOfTickets;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChosenFlight)) return false;
        if (!super.equals(o)) return false;
        ChosenFlight that = (ChosenFlight) o;
        return getNumOfTickets() == that.getNumOfTickets() &&
                Objects.equals(getFlight(), that.getFlight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumOfTickets(), getFlight());
    }

    @Override
    public String toString() {
        return "service.ChosenFlight{" +
                "numOfTickets=" + numOfTickets +
                ", flight=" + flight +
                '}';
    }
}
