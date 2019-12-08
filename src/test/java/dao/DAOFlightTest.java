package dao;

import org.junit.Test;

import static org.junit.Assert.*;

import entity.City;
import entity.Country;
import entity.Flight;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

public class DAOFlightTest {
    Flight flight1, flight2;
    City city1, city2, city3;
    Country country1, country2, country3;
    DAOFlight dof;

    @Before
    public void before() throws IOException, ParseException {
        country1 = new Country(1, "Norway");
        country2 = new Country(2, "Switzerland");
        country3 = new Country(3, "Australia");
        this.city1 = new City(1, "Oslo", country1);
        this.city2 = new City(2, "Bern", country2);
        this.city3 = new City(3, "Canberra", country3);
        this.flight1 = new Flight(1, city1, city2, 50, 50, LocalDateTime.now());
        this.flight2 = new Flight(64, city2, city3, 50, 50, LocalDateTime.now());
        this.dof = new DAOFlight();
    }

    @After
    public void after() {
        System.out.println("Test Finished");
    }

    @Test
    public void getAll1() {
        assertEquals(900, dof.getAll().size());
    }

    @Test
    public void get1() {
        assertEquals(1, dof.get(1).getId());
        assertEquals(city1, dof.get(1).getSource());
        assertEquals(city2, dof.get(1).getDestination());
        assertEquals(50, dof.get(1).getSeats());
        assertEquals(50, dof.get(1).getEmptySeats());
    }

    @Test
    public void get2() {
        assertEquals(64, dof.get(64).getId());
        assertEquals(city2, dof.get(64).getSource());
        assertEquals(city3, dof.get(64).getDestination());
        assertEquals(50, dof.get(64).getSeats());
        assertEquals(50, dof.get(64).getEmptySeats());
    }

    @Test
    public void put1() {
        dof.put(flight1);
        assertEquals(901, dof.nFlights());
    }

    @Test
    public void put2() {
        dof.put(flight1);
        dof.put(flight2);
        assertEquals(902, dof.nFlights());
    }

    @Test
    public void delete1() {
        dof.delete(flight1.getId());
        assertEquals(899, dof.nFlights());
    }

    @Test
    public void delete2() {
        dof.delete(flight1.getId());
        dof.delete(flight2.getId());
        assertEquals(898, dof.nFlights());
    }
}