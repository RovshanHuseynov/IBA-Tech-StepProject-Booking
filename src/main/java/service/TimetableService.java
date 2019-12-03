package service;

import console.SystemConsole;
import dao.DAOFlight;
import entity.Flight;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableService {
    private final SystemConsole systemConsole;
    private final DAOFlight daoFlight;
    private Menu menu;

    public TimetableService() throws IOException, ParseException {
        this.systemConsole = new SystemConsole();
        this.daoFlight = new DAOFlight();
        this.menu = new Menu();
    }

    public void show(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        try {
            List<Flight> all = daoFlight.getAll().stream().filter(flight -> {
                LocalDateTime checkDateTime = flight.getDate();
                boolean isDateTrue = (checkDateTime.compareTo(fromDateTime) >= 0) && (checkDateTime.compareTo(toDateTime) <= 0);
                return isDateTrue;
            }).collect(Collectors.toList());
            printFlights(all);
        } catch (Exception e) {
            systemConsole.printLn("Input is not correct");
        }
    }

    public void showLine(int id) {
        try {
            Flight flight = daoFlight.get(id);
            List<Flight> all = new ArrayList<>();
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.compareTo(flight.getDate()) <= 0) {
                all.add(flight);
                printFlights(all);
            } else {
                systemConsole.printLn("This flight is already outdated");
            }
        } catch (Exception e) {
            System.out.println("Input is not correct");
        }
    }

    public ChosenFlight search(String fromCityName, String toCityName, String date, String nTickets) {
        try {
            LocalDateTime currentDate = LocalDateTime.now();
            boolean isYearOld = (currentDate.getYear() > Integer.parseInt(date.split("\\.")[0]));

            boolean isMonthOld = (currentDate.getYear() == Integer.parseInt(date.split("\\.")[0]) &&
                    currentDate.getMonthValue() > Integer.parseInt(date.split("\\.")[1]));

            boolean isDayOld = (currentDate.getYear() == Integer.parseInt(date.split("\\.")[0]) &&
                    currentDate.getMonthValue() == Integer.parseInt(date.split("\\.")[1]) &&
                    currentDate.getDayOfMonth() > Integer.parseInt(date.split("\\.")[2]));

            boolean isDateOld = date.split("\\.").length == 3 && (isYearOld || isMonthOld || isDayOld);

            if (isDateOld) {
                systemConsole.printLn("Your input date is already outdated");
                return null;
            }

            List<Flight> all = daoFlight.getAll().stream().filter(flight -> {
                String from = flight.getSource().getName();
                String to = flight.getDestination().getName();
                int year = Integer.parseInt(date.split("\\.")[0]);
                int month = Integer.parseInt(date.split("\\.")[1]);
                int day = Integer.parseInt(date.split("\\.")[2]);
                boolean equalDate = (year == flight.getDate().getYear()) && (month == flight.getDate().getMonthValue()) && (day == flight.getDate().getDayOfMonth());
                boolean enoughEmptySeats = Integer.parseInt(nTickets) <= flight.getEmptySeats();
                return enoughEmptySeats
                        && from.equalsIgnoreCase(fromCityName)
                        && to.equalsIgnoreCase(toCityName)
                        && equalDate;
            }).collect(Collectors.toList());
            printFlights(all);
            if (all.size() > 0) {
                systemConsole.printLn("Please enter flight id to book a flight");
                String in = systemConsole.readLn();
                int flightId = checkInputIsInteger(in);
                if (flightId != -1) {
                    for (Flight flight : all) {
                        if (flight.getId() == flightId) {
                            int seats = flight.getEmptySeats() - Integer.parseInt(nTickets);
                            flight.setEmptySeats(seats);
                            daoFlight.set(flight);
                            int n = checkInputIsInteger(nTickets);
                            return new ChosenFlight(n, flight);
                        }
                    }
                    systemConsole.printLn("This flight ID was not in the list");
                }
            }
        } catch (Exception e) {
            systemConsole.printLn("Wrong input");
        }
        return null;
    }

    public void printFlights(List<Flight> all) {
        if (all.size() == 0) systemConsole.printLn("No database_files.Flights Found");
        else if (all.size() == 1) systemConsole.printLn(all.size() + " available flight found:");
        else systemConsole.printLn(all.size() + " available flights found:");
        for (Flight flight : all) {
            systemConsole.printLn("id:" + flight.getId()
                    + ", source:" + flight.getSource().getName()
                    + ", destination:" + flight.getDestination().getName()
                    + ", emptySeats:" + flight.getEmptySeats()
                    + ", date:" + flight.getDate());
        }
    }

    public int checkInputIsInteger(String input) {
        int id = -1;
        while (true) {
            try {
                id = Integer.parseInt(input);
                break;
            } catch (Exception e) {
                systemConsole.printLn("Please, enter an right integer : ");
                input = systemConsole.readLn();
            }
        }
        return id;
    }

    public void load() {
        daoFlight.getAll();
    }
}

