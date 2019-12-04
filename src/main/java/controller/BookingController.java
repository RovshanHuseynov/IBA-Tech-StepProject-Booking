package controller;

import console.Console;
import service.BookingService;
import service.ChosenFlight;

import java.io.IOException;
import java.text.ParseException;

public class BookingController {
    private final BookingService bookingService;
    private final Console console;


    public BookingController(Console console) throws IOException, ParseException {
        this.console = console;
        this.bookingService = new BookingService(console);
    }

    public void remove() throws IOException, ParseException {
        console.printLn("Write an id of your booking to cancel:");
        String line;
        int id = -1;
        while (true) {
            try {
                line = console.readLn();
                id = Integer.parseInt(line);
                break;
            } catch (Exception e) {
                console.printLn("Please, enter an valid id : ");
            }
        }
        bookingService.delete(id);
    }

    public void show() {
        console.printLn("Enter your name");
        String name = console.readLn();
        console.printLn("Enter your surname");
        String surname = console.readLn();
        bookingService.show(name.trim(), surname.trim());
    }

    public void add(ChosenFlight chosen) throws IOException, ParseException {
        bookingService.add(chosen);
    }

    public void load() throws IOException, ParseException {
        bookingService.load();
    }
}
