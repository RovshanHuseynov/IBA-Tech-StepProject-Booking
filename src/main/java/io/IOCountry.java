package io;

import entity.Country;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOCountry {
    private final String path = "data/countries.txt";
    private final File file = new File(path);


    public List<Country> read() throws IOException {
        List<Country> countries = new ArrayList<Country>();
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            int id = Integer.parseInt(line.split(" : ")[0]);
            String name = line.split(" : ")[1];
            countries.add(new Country(id, name));
        }
        br.close();
        return countries;
    }

    public void write(List<Country> countries) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        StringBuilder sb;
        for (Country c : countries) {
            sb = new StringBuilder();
            sb.append(c.getId());
            sb.append(" : ");
            sb.append(c.getName());
            bw.write(sb.toString());
            bw.newLine();
        }
        bw.close();
    }
}
