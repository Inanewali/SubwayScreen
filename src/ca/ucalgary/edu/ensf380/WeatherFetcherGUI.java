package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WeatherFetcherGUI extends JFrame {
    private JLabel temperatureLabel;
    private JLabel conditionLabel;
    private WeatherFetcher weatherFetcher;

    public WeatherFetcherGUI() {
        weatherFetcher = new WeatherFetcher();

        setTitle("Weather Fetcher");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        temperatureLabel = new JLabel("Temperature: ");
        conditionLabel = new JLabel("Condition: ");

        add(temperatureLabel);
        add(conditionLabel);

        fetchWeather();
    }

    private void fetchWeather() {
        try {
            String[] weatherData = weatherFetcher.fetchWeather();
            temperatureLabel.setText("Temperature: " + weatherData[0]);
            conditionLabel.setText("Condition: " + weatherData[1]);
        } catch (IOException e) {
            e.printStackTrace();
            temperatureLabel.setText("Temperature: Error fetching weather.");
            conditionLabel.setText("Condition: Error fetching weather.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherFetcherGUI frame = new WeatherFetcherGUI();
            frame.setVisible(true);
        });
    }
}
