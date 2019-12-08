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
        fromCityName = checkCityName(fromCityName);
        console.printLn("Please enter destination city name:");
        String toCityName = console.readLn();
        toCityName = checkCityName(toCityName);
        console.printLn("Please enter flight date: (Example: 2019.11.25)");
        String date = console.readLn();
        date = checkDate(date);
        console.printLn("Please enter number of tickets to buy:");
        String nTickets = console.readLn();
        nTickets = checkInteger(nTickets);
        ChosenFlight chosen = timetableService.search(fromCityName, toCityName, date, nTickets);
        if (chosen != null) {
            bookingController.add(chosen);
        }
    }

    public void show() {
        console.printLn("Please enter end date: (Example: 2019.11.25)");
        LocalDateTime fromDateTime = LocalDateTime.now();
        String endDate = checkDate(console.readLn().trim());
        int year = Integer.parseInt(endDate.split("\\.")[0]);
        int month = Integer.parseInt(endDate.split("\\.")[1]);
        int day = Integer.parseInt(endDate.split("\\.")[2]);
        LocalDateTime toDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
        timetableService.show(fromDateTime, toDateTime);
    }

    public void showLine() {
        console.printLn("Please enter flight id");
        String line = checkInteger(console.readLn());
        int id = Integer.parseInt(line);
        timetableService.showLine(id);
    }

    public void load() {
        timetableService.load();
    }

    public String checkCityName(String cityName) {
        boolean isValid;
        while (true) {
            isValid = true;
            cityName = cityName.trim();
            int len = cityName.length();
            for (int i = 0; i < len; i++) {
                if (!isLetter(cityName.charAt(i))) {
                    isValid = false;
                    break;
                }
            }

            if (!isValid) {
                console.printLn("Please enter city name correctly");
                cityName = console.readLn();
            } else {
                break;
            }
        }
        return cityName;
    }

    public boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public String checkInteger(String input) {
        boolean isValid;
        while (true) {
            isValid = true;
            input = input.trim();
            int len = input.length();
            for (int i = 0; i < len; i++) {
                if (!isInteger(input.charAt(i))) {
                    isValid = false;
                    break;
                }
            }

            if (!isValid) {
                console.printLn("Please enter integer correctly");
                input = console.readLn();
            } else {
                break;
            }
        }
        return input;
    }

    public boolean isInteger(char c) {
        return (c >= '0' && c <= '9');
    }

    public String checkDate(String input) {
        boolean isValid;
        while (true) {
            input = input.trim();
            String[] splitter = input.split("\\.");
            if (splitter.length != 3) {
                console.printLn("Please enter date correctly");
                input = console.readLn();
                continue;
            }

            int year = Integer.parseInt(splitter[0]);
            int month = Integer.parseInt(splitter[1]);
            int day = Integer.parseInt(splitter[2]);

            isValid = (year >= 0) && (month >= 1) && (month <= 12) && (day >= 1) && (day <= 31);

            if (!isValid) {
                console.printLn("Please enter date correctly");
                input = console.readLn();
            } else {
                LocalDateTime currentDate = LocalDateTime.now();
                boolean isYearOld = (currentDate.getYear() > year);
                boolean isMonthOld = (currentDate.getYear() == year &&
                        currentDate.getMonthValue() > month);
                boolean isDayOld = (currentDate.getYear() == year &&
                        currentDate.getMonthValue() == month &&
                        currentDate.getDayOfMonth() > day);
                boolean isDateOld = isYearOld || isMonthOld || isDayOld;
                isValid = !isDateOld;

                if (!isValid) {
                    console.printLn("Your input date is already outdated");
                    console.printLn("Please enter another date");
                    input = console.readLn();
                } else {
                    break;
                }
            }
        }
        return input;
    }
}
