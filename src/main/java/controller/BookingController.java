package controller;

import Console.SystemConsole;
import entity.Flight;
import service.BookingService;
import service.ChosenFlight;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class BookingController {
    private final BookingService bookingService;
    private final SystemConsole systemConsole;


    public BookingController() throws IOException, ParseException {
        this.bookingService = new BookingService();
        this.systemConsole = new SystemConsole();
    }

    public void remove() throws IOException, ParseException {
        systemConsole.printLn("Write an id of your booking to cancel:");
        String line;
        int id = -1;
        while (true) {
            try {
                line = systemConsole.readLn();
                id = Integer.parseInt(line);
                break;
            } catch (Exception e) {
                systemConsole.printLn("Please, enter an valid id : ");
            }
        }
        bookingService.delete(id);
    }

    public void show() {
        systemConsole.printLn("Enter your name");
        String name = systemConsole.readLn();
        systemConsole.printLn("Enter your surname");
        String surname = systemConsole.readLn();
        bookingService.show(name.trim(), surname.trim());
    }

    public void add(ChosenFlight chosen) throws IOException, ParseException {
        bookingService.add(chosen);
    }

    public void load() throws IOException, ParseException {
        bookingService.load();
    }
}
