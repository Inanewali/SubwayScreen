package ca.ucalgary.edu.ensf380.simulator;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TrainSimulator {
    private List<Train> trains;
    private Map<String, List<Station>> subwayLines;
    private Timer timer;
    private MapPanel mapPanel;

    public TrainSimulator(List<Train> trains, Map<String, List<Station>> subwayLines, MapPanel mapPanel) {
        this.trains = trains;
        this.subwayLines = subwayLines;
        this.timer = new Timer();
        this.mapPanel = mapPanel;

        // Debugging: Print initial positions
        printPositions();
    }

    public void startSimulation() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updatePositions();
                printPositions();
                mapPanel.updateTrains(trains);
            }
        }, 0, 15000); // Update every 15 seconds
    }

    private void updatePositions() {
        for (Train train : trains) {
            train.move(subwayLines);
        }
    }

    private void printPositions() {
        System.out.println("Current Train Positions:");
        for (Train train : trains) {
            System.out.println("Train on line " + train.line + " at station " + train.currentStation.stationName + 
                               " (" + train.currentStation.x + ", " + train.currentStation.y + ")");
        }
        System.out.println();
    }
    
}




