package service;

import Console.SystemConsole;
import dao.DAOBooking;
import dao.DAOFlight;
import entity.Booking;
import entity.Flight;
import entity.Passenger;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingService {
    private final SystemConsole systemConsole;
    private final DAOBooking daoBooking;
    private final DAOFlight daoFlight;

    public BookingService() throws IOException, ParseException {
        this.systemConsole = new SystemConsole();
        this.daoBooking = new DAOBooking();
        this.daoFlight = new DAOFlight();
    }

    public void add(HashMap<Integer, Flight> booking) throws IOException {
        int numOfTickets = 0;
        Flight flight = null;
        List<Passenger> passengers = new ArrayList<>(1);
        for (Map.Entry<Integer, Flight> entry : booking.entrySet()) {
            numOfTickets = entry.getKey();
            flight = entry.getValue();
        }
        systemConsole.printLn("Enter passengers name and surname with enter.");
        for (int i = 0; i < numOfTickets; i++) {
            systemConsole.printLn((i + 1) + ". Passenger name");
            String name = systemConsole.readLn().trim();
            systemConsole.printLn((i + 1) + ". Passenger surname");
            String surname = systemConsole.readLn().trim();
            passengers.add(new Passenger(name, surname));
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        now = LocalDateTime.parse(formatDateTime, formatter);
        Booking booked = new Booking(flight, passengers, now, passengers.get(0));
        daoBooking.put(booked);
    }

    public void delete(int booking_id) throws IOException {
        daoBooking.delete(booking_id);
    }

    public void show(String name, String surname) {
        List<Booking> all = daoBooking.getAll();
        boolean f = false;
        for (Booking booking : all) {
            for (Passenger passenger : booking.getPassengers()) {
                if (name.equalsIgnoreCase(passenger.getName()) && surname.equalsIgnoreCase(passenger.getSurname())) {
                    printBooking(booking);
                    f = true;
                }
            }
        }
        if (!f) {
            systemConsole.printLn("There is no booking at your name!");
        }
    }

    public void printBooking(Booking booking) {
        systemConsole.printLn("Buyer: " + booking.getBuyer()
                + ", flight from: " + booking.getFlight().getSource().getName().toUpperCase()
                + ", to : " + booking.getFlight().getDestination().getName().toUpperCase()
                + ", flight date : " + booking.getFlight().getDate()
                + ", " + booking.getPassengers().toString()
                + ", booking date : " + booking.getDate());

    }

    public void load() throws IOException {
        daoBooking.getAll();
    }

}
