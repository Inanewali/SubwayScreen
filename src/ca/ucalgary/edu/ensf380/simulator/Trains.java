package ca.ucalgary.edu.ensf380.simulator;

import java.util.List;
import java.util.Map;

public class Trains {
    String line;
    Station currentStation;
    int direction;
	private Map<String, Station> stationMap;
    
    public void setStationMap(Map<String, Station> stationMap) {
        this.stationMap = stationMap;
    }

    public Trains(String line, Station currentStation, int direction, Map<String, Station> stationMap) {
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
        announceNextStation();
    }
    public void announceNextStation() {
        String nextStationName = getNextStationName();
        Station nextStation = stationMap.get(nextStationName);

        if (nextStation != null) {
            String announcement = "Next stop: " + nextStation.getName();
            String hint = getChangeLineHint(nextStation);

            if (hint != null && !hint.isEmpty()) {
                announcement += ", you can change your train to line " + hint;
            }

            TextToSpeech.speak(announcement);
        }
    }
    private String getNextStationName() {
        // Logic to determine the next station name based on the current station and direction
        return "NextStation"; // Placeholder, replace with actual logic
    }
    

    private String getChangeLineHint(Station station) {
        StringBuilder hint = new StringBuilder();
        for (Map.Entry<String, Station> entry : stationMap.entrySet()) {
            if (entry.getValue().getCode().equals(station.getCode()) && !entry.getValue().getLine().equals(this.line)) {
                if (hint.length() > 0) {
                    hint.append(", ");
                }
                hint.append(entry.getValue().getLine());
            }
        }
        return hint.toString();
    }
    
}
