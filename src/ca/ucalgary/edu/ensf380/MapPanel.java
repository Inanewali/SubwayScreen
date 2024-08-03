package ca.ucalgary.edu.ensf380;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapPanel extends JPanel {
    private Map<String, List<Station>> subwayLines;
    private List<Train> trains;

    public MapPanel(Map<String, List<Station>> subwayLines, List<Train> trains) {
        this.subwayLines = subwayLines;
        this.trains = trains;
        setPreferredSize(new Dimension(1200, 800));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate scaling factors
        double xScale = getWidth() / 1200.0; // Scale based on preferred width
        double yScale = getHeight() / 800.0; // Scale based on preferred height

        g2d.scale(xScale, yScale);
        drawSubwayLines(g);
        drawTrains(g);
    }

    private void drawSubwayLines(Graphics g) {
        Map<String, Color> lineColors = new HashMap<>();
        lineColors.put("R", Color.RED);
        lineColors.put("G", Color.GREEN);
        lineColors.put("B", Color.BLUE);

        for (String line : subwayLines.keySet()) {
            List<Station> stations = subwayLines.get(line);
            g.setColor(lineColors.getOrDefault(line, Color.BLACK));
            for (int i = 0; i < stations.size() - 1; i++) {
                Station s1 = stations.get(i);
                Station s2 = stations.get(i + 1);
                g.drawLine((int) s1.getX(), (int) s1.getY(), (int) s2.getX(), (int) s2.getY());
                // Draw station names
//                drawStationName(g, s1);
            }
//            // Draw the name of the last station
//            drawStationName(g, stations.get(stations.size() - 1));
        }
    }

//    private void drawStationName(Graphics g, Station station) {
//        g.setColor(Color.BLACK); // Set the color for the text
//        g.drawString(station.getName(), (int) station.getX() + 5, (int) station.getY() - 5); // Adjust the position as needed
//    }


    private void drawTrains(Graphics g) {
        Map<String, Color> lineColors = new HashMap<>();
        lineColors.put("R", Color.RED);
        lineColors.put("G", Color.GREEN);
        lineColors.put("B", Color.BLUE);

        for (Train train : trains) {
            // Set color based on the line
            g.setColor(lineColors.getOrDefault(train.line, Color.BLACK));
            g.fillOval((int) train.currentStation.getX() - 5, (int) train.currentStation.getY() - 5, 10, 10);
        }
    }

    public void updateTrains(List<Train> trains) {
        this.trains = trains;
        repaint();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 800); // Ensure the preferred size is set
    }
}
