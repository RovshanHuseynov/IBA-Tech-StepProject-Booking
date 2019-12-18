package service;

import console.Console;
import dao.DAOFlight;
import entity.Flight;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private final Console console;
    private final DAOFlight daoFlight;

    public FlightService(Console console) throws IOException, ParseException {
        this.console = console;
        this.daoFlight = new DAOFlight();
    }

    public void show(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        List<Flight> all = daoFlight.getAll().stream().filter(flight -> {
            LocalDateTime checkDateTime = flight.getDate();
            return (checkDateTime.compareTo(fromDateTime) >= 0) && (checkDateTime.compareTo(toDateTime) <= 0);
        }).collect(Collectors.toList());
        printFlights(all);
    }

    public void showLine(int id) {
        try {
            if (id > daoFlight.size()) {
                console.printLn("No Flights Found");
            } else {
                Flight flight = daoFlight.get(id);
                List<Flight> all = new ArrayList<>();
                LocalDateTime currentTime = LocalDateTime.now();
                if (currentTime.compareTo(flight.getDate()) <= 0) {
                    all.add(flight);
                    printFlights(all);
                } else {
                    console.printLn("This flight is already outdated");
                }
            }
        } catch (Exception e) {
            System.out.println("Input is not correct");
        }
    }

    public ChosenFlight search(String fromCityName, String toCityName, String date, String nTickets) {
        try {
            String[] splitter = date.split("\\.");
            int year = Integer.parseInt(splitter[0]);
            int month = Integer.parseInt(splitter[1]);
            int day = Integer.parseInt(splitter[2]);

            List<Flight> all = daoFlight.getAll().stream().filter(flight -> {
                String from = flight.getSource().getName();
                String to = flight.getDestination().getName();

                boolean equalDate = (year == flight.getDate().getYear())
                        && (month == flight.getDate().getMonthValue())
                        && (day == flight.getDate().getDayOfMonth());
                boolean enoughEmptySeats = Integer.parseInt(nTickets) <= flight.getEmptySeats();
                return enoughEmptySeats
                        && equalDate
                        && from.equalsIgnoreCase(fromCityName)
                        && to.equalsIgnoreCase(toCityName);
            }).collect(Collectors.toList());

            printFlights(all);

            if (all.size() > 0) {
                console.printLn("Please enter flight id to book a flight");
                String in = console.readLn();
                in = checkInteger(in);
                int flightId = Integer.parseInt(in);

                if (flightId > daoFlight.size()) {
                    console.printLn("This flight ID was not in the list");
                } else {
                    Flight flight = daoFlight.get(flightId);
                    if (flight.getEmptySeats() >= Integer.parseInt(nTickets)
                            && flight.getDate().getYear() == year
                            && flight.getDate().getMonthValue() == month
                            && flight.getDate().getDayOfMonth() == day
                            && flight.getSource().getName().toLowerCase().equals(fromCityName.toLowerCase())
                            && flight.getDestination().getName().toLowerCase().equals(toCityName.toLowerCase())) {
                        int seats = flight.getEmptySeats() - Integer.parseInt(nTickets);
                        flight.setEmptySeats(seats);
                        daoFlight.update(flight);
                        return new ChosenFlight(Integer.parseInt(nTickets), flight);
                    } else {
                        console.printLn("This flight ID was not in the list");
                    }
                }
            }
        } catch (
                Exception e) {
            console.printLn("Wrong input");
        }
        return null;
    }

    public void printFlights(List<Flight> all) {
        if (all.size() == 0) console.printLn("No Flights Found");
        else if (all.size() == 1) console.printLn(all.size() + " available flight found:");
        else console.printLn(all.size() + " available flights found:");
        for (Flight flight : all) {
            console.printLn("id:" + flight.getId()
                    + ", source:" + flight.getSource().getName()
                    + ", destination:" + flight.getDestination().getName()
                    + ", emptySeats:" + flight.getEmptySeats()
                    + ", date:" + flight.getDate());
        }
    }

    public void load() {
        daoFlight.getAll();
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
}

