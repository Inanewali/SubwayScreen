package ca.ucalgary.edu.ensf380.simulator;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class SubwaySimulator {
    public static void main(String[] args) {
        String inputFilePath = args[1];
        String outputFolderPath = args[3];

        Map<String, List<Station>> subwayLines = SubwayParser.parseCSV(inputFilePath);
        List<Train> trains = TrainInitializer.initializeTrains(subwayLines);

        JFrame frame = new JFrame("Subway Simulator");
        MapPanel mapPanel = new MapPanel(subwayLines, trains);
        frame.add(mapPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        TrainSimulator simulator = new TrainSimulator(trains, subwayLines, mapPanel);
        simulator.startSimulation();
    }
}

