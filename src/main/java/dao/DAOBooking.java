package dao;

import Console.SystemConsole;
import entity.Booking;
import entity.Flight;
import io.IOBooking;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DAOBooking implements DAO<Booking> {
    private final IOBooking ioBooking;
    private final DAOFlight daoFlight;
    private final SystemConsole systemConsole;
    private List<Booking> bookings;

    public DAOBooking() throws IOException, ParseException {
        this.bookings = new IOBooking().read();
        this.ioBooking = new IOBooking();
        this.daoFlight = new DAOFlight();
        this.systemConsole = new SystemConsole();
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
        systemConsole.printLn("Your flight successfully booked!");
        ioBooking.updateFile(bookings);
        this.bookings = ioBooking.read();

    }

    public void delete(int id) throws IOException, ParseException {
        for (int i = 0; i < bookings.size(); i++) {
            if (id == bookings.get(i).getFlight().getId()) {
                int seats = bookings.get(i).getFlight().getEmptySeats() + bookings.get(i).getPassengers().size();
                Flight flight = bookings.get(i).getFlight();
                flight.setEmptySeats(seats);
                daoFlight.set(flight);
                bookings.remove(bookings.get(i));
                ioBooking.updateFile(bookings);
                this.bookings = ioBooking.read();
                systemConsole.printLn("Your booking successfully cancelled!");
            } else {
                systemConsole.printLn("No booking at this id!");
            }
        }
    }
}
