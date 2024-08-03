package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class StationParser {
    public static Map<String, StationInfo> parseCSV(String filePath) {
        Map<String, StationInfo> stationMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] values = line.split(",");
                String stationName = values[0];
                String lineColor = values[1];
                String commonStationCode = values[7]; // Column H

                StationInfo stationInfo = stationMap.getOrDefault(stationName, new StationInfo(stationName));
                stationInfo.addLine(lineColor);

                if (!commonStationCode.isEmpty()) {
                    stationInfo.addCommonStationCode(commonStationCode);
                }

                stationMap.put(stationName, stationInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationMap;
    }

    public static class StationInfo {
        private String name;
        private List<String> lines;
        private List<String> commonStationCodes;

        public StationInfo(String name) {
            this.name = name;
            this.lines = new ArrayList<>();
            this.commonStationCodes = new ArrayList<>();
        }

        public void addLine(String line) {
            if (!lines.contains(line)) {
                lines.add(line);
            }
        }

        public void addCommonStationCode(String code) {
            if (!commonStationCodes.contains(code)) {
                commonStationCodes.add(code);
            }
        }

        public String getName() {
            return name;
        }

        public List<String> getLines() {
            return lines;
        }

        public List<String> getCommonStationCodes() {
            return commonStationCodes;
        }
    }
}

