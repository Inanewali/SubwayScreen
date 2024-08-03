package ca.ucalgary.edu.ensf380;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubwayDataLoader {
    public static List<double[]> loadCoordinates(String filePath) {
        List<double[]> coordinates = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            boolean isFirstLine = true;
            while ((line = reader.readNext()) != null) {
                // Skip the header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                double x = Double.parseDouble(line[0]);
                double y = Double.parseDouble(line[1]);
                coordinates.add(new double[]{x, y});
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return coordinates;
    }
}
