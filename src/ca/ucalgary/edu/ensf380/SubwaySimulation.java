package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SubwaySimulation extends JFrame {
    private SubwayMapPanel mapPanel;
    private Timer timer;
    private int trainPositionBlue = 0;
    private int trainPositionGreen = 0;
    private int trainPositionRed = 0;

    private List<double[]> blueLine;
    private List<double[]> greenLine;
    private List<double[]> redLine;

    public SubwaySimulation(List<double[]> blueLine, List<double[]> greenLine, List<double[]> redLine) {
        this.blueLine = blueLine;
        this.greenLine = greenLine;
        this.redLine = redLine;

        mapPanel = new SubwayMapPanel(blueLine, greenLine, redLine);
        add(mapPanel);
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(100, e -> {
            moveTrains();
            mapPanel.repaint();
        });
        timer.start();
    }

    private void moveTrains() {
        trainPositionBlue = (trainPositionBlue + 1) % blueLine.size();
        trainPositionGreen = (trainPositionGreen + 1) % greenLine.size();
        trainPositionRed = (trainPositionRed + 1) % redLine.size();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        mapPanel.drawTrain(g, blueLine, trainPositionBlue, Color.BLUE);
        mapPanel.drawTrain(g, greenLine, trainPositionGreen, Color.GREEN);
        mapPanel.drawTrain(g, redLine, trainPositionRed, Color.RED);
    }

    public static void main(String[] args) {
        List<double[]> blueLine = SubwayDataLoader.loadCoordinates("map/Blue.csv");
        List<double[]> greenLine = SubwayDataLoader.loadCoordinates("map/Green.csv");
        List<double[]> redLine = SubwayDataLoader.loadCoordinates("map/Red.csv");

        SwingUtilities.invokeLater(() -> {
            SubwaySimulation simulation = new SubwaySimulation(blueLine, greenLine, redLine);
            simulation.setVisible(true);
        });
    }
}
