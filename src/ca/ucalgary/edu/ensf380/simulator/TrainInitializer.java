package ca.ucalgary.edu.ensf380.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrainInitializer {
    public static List<Train> initializeTrains(Map<String, List<Station>> subwayLines) {
        List<Train> trains = new ArrayList<>();
        int trainsPerLine = 4; // 12 trains on 3 lines
        for (String line : subwayLines.keySet()) {
            List<Station> stations = subwayLines.get(line);
            for (int i = 0; i < trainsPerLine; i++) {
                Station station = stations.get(i * 4);
                trains.add(new Train(line, station, 1)); // All trains move forward initially
            }
        }
        return trains;
    }
}

class Train {
    String line;
    Station currentStation;
    int direction;

    public Train(String line, Station currentStation, int direction) {
        this.line = line;
        this.currentStation = currentStation;
        this.direction = direction;
    }

    public void move(Map<String, List<Station>> subwayLines) {
        List<Station> stations = subwayLines.get(line);
        int currentIndex = stations.indexOf(currentStation);
        int newIndex = currentIndex + direction;
        if (newIndex < 0 || newIndex >= stations.size()) {
            direction *= -1; // Change direction at the end
            newIndex = currentIndex + direction;
        }
        currentStation = stations.get(newIndex);
    }
}
