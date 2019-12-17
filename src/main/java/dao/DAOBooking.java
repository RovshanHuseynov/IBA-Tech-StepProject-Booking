package dao;

import console.SystemConsole;
import console.Console;
import entity.Booking;
import entity.Flight;
import io.IOBooking;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DAOBooking implements DAO<Booking> {
    private final IOBooking ioBooking;
    private final DAOFlight daoFlight;
    private final Console console;
    private List<Booking> bookings;

    public DAOBooking() throws IOException, ParseException {
        this.bookings = new IOBooking().read();
        this.ioBooking = new IOBooking();
        this.daoFlight = new DAOFlight();
        this.console = new SystemConsole();
    }

    public Booking get(int id) {
        for (Booking booking : bookings) {
            if (id == booking.getFlight().getId()) {
                return booking;
            }
        }
        throw new IllegalArgumentException("No booking at this id!");
    }

    public List<Booking> getAll() {
        return bookings;
    }

    public void put(Booking booking) throws IOException, ParseException {
        bookings.add(booking);
        console.printLn("Your new booking is getting ready. Please keep waiting...");
        ioBooking.updateFile(bookings);
        this.bookings = ioBooking.read();
        console.printLn("Your flight was successfully booked!");
    }

    public void delete(int id) throws IOException, ParseException {
        boolean isFound = false;
        for (int i = 0; i < bookings.size(); i++) {
            if (id == bookings.get(i).getFlight().getId()) {
                Booking curBooking = bookings.get(i);
                if (!isFound) {
                    console.printLn("Your booking is being cancelled. Please keep waiting..");
                    isFound = true;
                }
                int seats = curBooking.getFlight().getEmptySeats() + curBooking.getPassengers().size();
                Flight curFlight = curBooking.getFlight();
                curFlight.setEmptySeats(seats);
                daoFlight.update(curFlight);
                bookings.remove(curBooking);
                ioBooking.updateFile(bookings);
                this.bookings = ioBooking.read();
            }
        }

        if (!isFound) {
            console.printLn("No booking found at this id!");
        } else {
            console.printLn("Your booking was successfully cancelled!");
        }
    }
}
