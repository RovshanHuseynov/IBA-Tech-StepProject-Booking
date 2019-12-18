package io;

import entity.City;
import entity.Flight;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IOFlight {
    private final String path = "data/flights.txt";
    private final File file = new File(path);

    public List<Flight> read() throws IOException, ParseException {
        List<Flight> flights = new ArrayList<Flight>();
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            flights.add(defineFlight(line));
        }

        br.close();
        return flights;
    }

    public void write(List<Flight> flights) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        StringBuilder sb;
        for (Flight f : flights) {
            sb = new StringBuilder();
            sb.append(f.getId());
            sb.append(" : ");
            sb.append(f.getSource().getName());
            sb.append(" : ");
            sb.append(f.getDestination().getName());
            sb.append(" : ");
            sb.append(f.getSeats());
            sb.append(" : ");
            sb.append(f.getEmptySeats());
            sb.append(" : ");
            sb.append(f.getDate());
            bw.write(sb.toString());
            bw.newLine();
        }
        bw.close();
    }

    public Flight defineFlight(String line) throws IOException, ParseException {
        IOCity ioCity = new IOCity();
        List<City> cities = ioCity.read();
        String[] split = line.split(" : ");
        int id = Integer.parseInt(split[0]);
        String citySource = split[1];
        String cityDes = split[2];
        int seats = Integer.parseInt(split[3]);
        int emptySeats = Integer.parseInt(split[4]);
        LocalDateTime date = LocalDateTime.parse(split[5]);
        City source = null;
        City destination = null;
        for (City city : cities) {
            if (city.getName().equals(citySource)) {
                source = city;
            }
            if (city.getName().equals(cityDes)) {
                destination = city;
            }
        }
        return new Flight(id, source, destination, seats, emptySeats, date);
    }

    public void updateFile(List<Flight> flights) throws IOException {
        deleteFile(file);
        file.createNewFile();
        write(flights);
    }

    public void deleteFile(File file) {
        file.delete();
    }
}
