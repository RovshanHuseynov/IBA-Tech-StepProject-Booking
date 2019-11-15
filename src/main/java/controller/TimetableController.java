package controller;

import Console.SystemConsole;
import service.ChosenFlight;
import service.TimetableService;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

public class TimetableController {
    private final TimetableService timetableService;
    private final SystemConsole systemConsole;
    private final BookingController bookingController;

    public TimetableController() throws IOException, ParseException {
        this.timetableService = new TimetableService();
        this.systemConsole = new SystemConsole();
        this.bookingController = new BookingController();
    }

    public void search() throws IOException, ParseException {
        systemConsole.printLn("Please enter source city name:");
        String fromCityName = systemConsole.readLn();
        systemConsole.printLn("Please enter destination city name:");
        String toCityName = systemConsole.readLn();
        systemConsole.printLn("Please enter flight date: (Example: 2019.11.25)");
        String date = systemConsole.readLn();
        systemConsole.printLn("Please enter number of tickets to buy:");
        String nTickets = systemConsole.readLn();
        ChosenFlight chosen = timetableService.search(fromCityName.trim(), toCityName.trim(), date.trim(), nTickets.trim());
        if (chosen != null) {
            bookingController.add(chosen);
        }
    }

    public void show() {
        try {
            systemConsole.printLn("Please enter end date: (Example: 2019.11.25)");
            LocalDateTime fromDateTime = LocalDateTime.now();
            String endDate = systemConsole.readLn().trim();
            int year = Integer.parseInt(endDate.split("\\.")[0]);
            int month = Integer.parseInt(endDate.split("\\.")[1]);
            int day = Integer.parseInt(endDate.split("\\.")[2]);
            LocalDateTime toDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
            timetableService.show(fromDateTime, toDateTime);
        }catch (Exception e){
            systemConsole.printLn("Wrong Input");
        }
    }

    public void showLine() {
        systemConsole.printLn("Please enter flight id");
        String line;
        int id = -1;
        while (true) {
            try {
                line = systemConsole.readLn().trim();
                id = Integer.parseInt(line);
                break;
            } catch (Exception e) {
                systemConsole.printLn("Please, enter an integer : ");
            }
        }
        timetableService.showLine(id);
    }

    public void load()  {
        timetableService.load();
    }
}
