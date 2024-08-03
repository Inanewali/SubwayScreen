package ca.ucalgary.edu.ensf380;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainStationDisplay extends JPanel {
    private List<String> stations;
    private int currentStationIndex;

    public TrainStationDisplay(String csvFilePath) {
        stations = loadStationNames(csvFilePath);
        currentStationIndex = 0; // Start with the first station
        setPreferredSize(new Dimension(800, 400));
    }

    private List<String> loadStationNames(String csvFilePath) {
        List<String> stationNames = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> records = reader.readAll();
            for (int i = 1; i < records.size(); i++) { // Skip the header row
                stationNames.add(records.get(i)[4].trim());
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return stationNames;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        int stationCount = 5;
        int startX = 50;
        int stationGap = (width - 100) / (stationCount - 1);

        // Draw stations
        for (int i = 0; i < stationCount; i++) {
            int stationIndex = currentStationIndex - 1 + i;
            if (stationIndex >= 0 && stationIndex < stations.size()) {
                int x = startX + i * stationGap;
                g2d.setColor(Color.GRAY);
                if (i == 1) {
                    g2d.setColor(Color.RED); // Current station in red
                }
                g2d.fillOval(x - 10, height / 2 - 10, 20, 20);

                // Draw station name
                g2d.setColor(Color.BLACK);
                String stationName = stations.get(stationIndex);
                g2d.drawString(stationName, x - g2d.getFontMetrics().stringWidth(stationName) / 2, height / 2 + 30);
            }
        }

        // Draw direction arrow
        g2d.setColor(Color.RED);
        int arrowX = startX + stationGap;
        int arrowY = height / 2 - 5;
        g2d.fillPolygon(new int[]{arrowX, arrowX + 20, arrowX}, new int[]{arrowY - 10, arrowY, arrowY + 10}, 3);

        // Draw "Next:" text
        g2d.setColor(Color.BLACK);
        if (currentStationIndex + 1 < stations.size()) {
            String nextStation = "Next: " + stations.get(currentStationIndex + 1);
            g2d.drawString(nextStation, startX, height - 20);
        }
    }

    public void setCurrentStationIndex(int index) {
        this.currentStationIndex = index;
        repaint();
    }

    public void nextStation() {
        currentStationIndex = (currentStationIndex + 1) % stations.size();
        repaint();
    }
}
