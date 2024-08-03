package ca.ucalgary.edu.ensf380;

import java.io.*;
import java.util.*;

public class SubwayParser {
    public static Map<String, List<Station>> parseCSV(String filePath) {
        Map<String, List<Station>> subwayLines = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] values = line.split(",");
                String lineId = values[1];
                Station station = new Station(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4], 
                                              Double.parseDouble(values[5]), Double.parseDouble(values[6]));
                subwayLines.computeIfAbsent(lineId, k -> new ArrayList<>()).add(station);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subwayLines;
    }
}


