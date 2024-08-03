package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class SubwayMapPanel extends JPanel {
    private List<double[]> blueLine;
    private List<double[]> greenLine;
    private List<double[]> redLine;
    private BufferedImage mapImage;

    public SubwayMapPanel(List<double[]> blueLine, List<double[]> greenLine, List<double[]> redLine) {
        this.blueLine = blueLine;
        this.greenLine = greenLine;
        this.redLine = redLine;

        try {
            mapImage = ImageIO.read(new File("path/to/your/map/Trains.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the map image
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw the subway lines
        drawLine(g, blueLine, Color.BLUE);
        drawLine(g, greenLine, Color.GREEN);
        drawLine(g, redLine, Color.RED);
    }

    private void drawLine(Graphics g, List<double[]> line, Color color) {
        g.setColor(color);
        for (int i = 0; i < line.size() - 1; i++) {
            int x1 = (int) line.get(i)[0];
            int y1 = (int) line.get(i)[1];
            int x2 = (int) line.get(i + 1)[0];
            int y2 = (int) line.get(i + 1)[1];
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public void drawTrain(Graphics g, List<double[]> line, int position, Color color) {
        double[] trainPosition = line.get(position);
        g.setColor(color);
        g.fillOval((int) trainPosition[0] - 5, (int) trainPosition[1] - 5, 10, 10);
    }
}
