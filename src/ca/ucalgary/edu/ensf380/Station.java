package ca.ucalgary.edu.ensf380;

public class Station {
    String row;
    String line;
    int stationNumber;
    String stationCode;
    String stationName;
    double x;
    double y;

    public Station(String row, String line, int stationNumber, String stationCode, String stationName, double x, double y) {
        this.row = row;
        this.line = line;
        this.stationNumber = stationNumber;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
        
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public String getName() {
        return stationName;
    }
    public String getLine() {
        return line;
    }
    public String getCode() {
        return stationCode;
    }
    
}