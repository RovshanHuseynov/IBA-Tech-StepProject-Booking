package service;

import console.Console;
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
import java.util.List;

public class BookingService {
    private final Console console;
    private final DAOBooking daoBooking;
    private final DAOFlight daoFlight;

    public BookingService(Console console) throws IOException, ParseException {
        this.console = console;
        this.daoBooking = new DAOBooking();
        this.daoFlight = new DAOFlight();
    }

    public void add(ChosenFlight booking) throws IOException, ParseException {
        List<Passenger> passengers = new ArrayList<>(1);
        int numOfTickets = booking.getNumOfTickets();
        Flight flight = booking.getFlight();
        console.printLn("Enter passengers name and surname with enter.");
        for (int i = 0; i < numOfTickets; i++) {
            console.printLn((i + 1) + ". Passenger name");
            String name = console.readLn().trim();
            console.printLn((i + 1) + ". Passenger surname");
            String surname = console.readLn().trim();
            passengers.add(new Passenger(name, surname));
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        now = LocalDateTime.parse(formatDateTime, formatter);
        Booking booked = new Booking(flight, passengers, now, passengers.get(0));
        daoBooking.put(booked);
    }

    public void delete(int bookingId) throws IOException, ParseException {
        daoBooking.delete(bookingId);
    }

    public void show(String name, String surname) {
        List<Booking> all = daoBooking.getAll();
        boolean f = false;
        for (Booking booking : all) {
            if (name.equalsIgnoreCase(booking.getBuyer().getName()) && surname.equalsIgnoreCase(booking.getBuyer().getSurname())) {
                printBooking(booking);
                f = true;
            }
        }
        if (!f) {
            console.printLn("There is no booking at your name!");
        }
    }

    public void printBooking(Booking booking) {
        console.printLn("Buyer: " + booking.getBuyer().getName().toUpperCase()
                + " " + booking.getBuyer().getSurname().toUpperCase()
                + ", flight from: " + booking.getFlight().getSource().getName().toUpperCase()
                + ", to : " + booking.getFlight().getDestination().getName().toUpperCase()
                + ", flight date : " + booking.getFlight().getDate()
                + ", " + booking.getPassengers().toString().toUpperCase()
                + ", booking date : " + booking.getDate());


    }

    public void load() throws IOException, ParseException {
        daoBooking.getAll();
    }
}
