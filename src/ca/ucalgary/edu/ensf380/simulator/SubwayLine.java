package ca.ucalgary.edu.ensf380.simulator;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

public class SubwayLine {
    private List<Point> points;
    private Color color;

    public SubwayLine(List<Point> points, Color color) {
        this.points = points;
        this.color = color;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Color getColor() {
        return color;
    }
}
