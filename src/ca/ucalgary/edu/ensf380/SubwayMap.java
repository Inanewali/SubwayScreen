package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class SubwayMap extends JPanel {
    private List<Point> blueLine = new ArrayList<>();
    private List<Point> redLine = new ArrayList<>();
    private List<Point> greenLine = new ArrayList<>();
    private List<Station> stations = new ArrayList<>();
    private int blueTrainPos = 0;
    private int redTrainPos = 0;
    private int greenTrainPos = 0;
    
    public SubwayMap() {
        loadStations();
        loadLines();
        javax.swing.Timer timer = new javax.swing.Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveTrains();
                repaint();
            }
        });
        timer.start();
    }

    private void loadStations() {
        try (BufferedReader br = new BufferedReader(new FileReader("map/subway.csv"))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    stations.add(new Station(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLines() {
        loadLine("map/Blue.csv", blueLine);
        loadLine("map/Red.csv", redLine);
        loadLine("map/Green.csv", greenLine);
    }

    private void loadLine(String filePath, List<Point> line) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String csvLine;
            br.readLine(); // Skip header line
            while ((csvLine = br.readLine()) != null) {
                String[] parts = csvLine.split(",");
                if (parts.length >= 2) {
                    line.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveTrains() {
        blueTrainPos = (blueTrainPos + 1) % blueLine.size();
        redTrainPos = (redTrainPos + 1) % redLine.size();
        greenTrainPos = (greenTrainPos + 1) % greenLine.size();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);
        drawLines(g);
        drawStations(g);
        drawTrains(g);
    }

    private void drawMap(Graphics g) {
        Image mapImage = new ImageIcon("map/Map.png").getImage();
        g.drawImage(mapImage, 0, 0, this);
    }

    private void drawLines(Graphics g) {
        g.setColor(Color.BLUE);
        drawLine(g, blueLine);
        g.setColor(Color.RED);
        drawLine(g, redLine);
        g.setColor(Color.GREEN);
        drawLine(g, greenLine);
    }

    private void drawLine(Graphics g, List<Point> line) {
        for (int i = 0; i < line.size() - 1; i++) {
            Point p1 = line.get(i);
            Point p2 = line.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    private void drawStations(Graphics g) {
        g.setColor(Color.BLACK);
        for (Station station : stations) {
            g.fillOval(station.x - 5, station.y - 5, 10, 10);
            g.drawString(station.name, station.x + 5, station.y - 5);
        }
    }

    private void drawTrains(Graphics g) {
        g.setColor(Color.BLUE);
        drawTrain(g, blueLine.get(blueTrainPos));
        g.setColor(Color.RED);
        drawTrain(g, redLine.get(redTrainPos));
        g.setColor(Color.GREEN);
        drawTrain(g, greenLine.get(greenTrainPos));
    }

    private void drawTrain(Graphics g, Point p) {
        g.fillRect(p.x - 5, p.y - 5, 10, 10);
    }

    private static class Station {
        String name;
        int x, y;

        Station(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Subway Map");
        SubwayMap map = new SubwayMap();
        frame.add(map);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
