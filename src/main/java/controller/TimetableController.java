package controller;

import console.Console;
import service.ChosenFlight;
import service.TimetableService;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

public class TimetableController {
    private final TimetableService timetableService;
    private final Console console;
    private final BookingController bookingController;

    public TimetableController(Console console) throws IOException, ParseException {
        this.console = console;
        this.timetableService = new TimetableService(console);
        this.bookingController = new BookingController(console);
    }

    public void search() throws IOException, ParseException {
        console.printLn("Please enter source city name:");
        String fromCityName = console.readLn();
        console.printLn("Please enter destination city name:");
        String toCityName = console.readLn();
        console.printLn("Please enter flight date: (Example: 2019.11.25)");
        String date = console.readLn();
        console.printLn("Please enter number of tickets to buy:");
        String nTickets = console.readLn();
        ChosenFlight chosen = timetableService.search(fromCityName.trim(), toCityName.trim(), date.trim(), nTickets.trim());
        if (chosen != null) {
            bookingController.add(chosen);
        }
    }

    public void show() {
        try {
            console.printLn("Please enter end date: (Example: 2019.11.25)");
            LocalDateTime fromDateTime = LocalDateTime.now();
            String endDate = console.readLn().trim();
            int year = Integer.parseInt(endDate.split("\\.")[0]);
            int month = Integer.parseInt(endDate.split("\\.")[1]);
            int day = Integer.parseInt(endDate.split("\\.")[2]);
            LocalDateTime toDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
            timetableService.show(fromDateTime, toDateTime);
        }catch (Exception e){
            console.printLn("Wrong Input");
        }
    }

    public void showLine() {
        console.printLn("Please enter flight id");
        String line;
        int id = -1;
        while (true) {
            try {
                line = console.readLn().trim();
                id = Integer.parseInt(line);
                break;
            } catch (Exception e) {
                console.printLn("Please, enter an integer : ");
            }
        }
        timetableService.showLine(id);
    }

    public void load()  {
        timetableService.load();
    }
}
