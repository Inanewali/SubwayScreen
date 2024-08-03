package ca.ucalgary.edu.ensf380;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WeatherFetcher {

    private static final String URL = "https://wttr.in/Calgary,CA";

    public String[] fetchWeather() throws IOException {
        // Fetch the HTML content from the URL
        Document document = Jsoup.connect(URL).get();

        // Parse the HTML to get the temperature and weather description
        Element weatherElement = document.selectFirst("pre");
        if (weatherElement != null) {
            String weatherText = weatherElement.text();

            // Extract the temperature and weather condition
            String temperature = extractTemperature(weatherText);
            String condition = extractCondition(weatherText);

            return new String[]{temperature, condition};
        } else {
            return new String[]{"Weather information not found.", "Weather information not found."};
        }
    }

    private String extractTemperature(String weatherText) {
        String tempPattern = "(-?\\d+\\s?°C)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(tempPattern);
        java.util.regex.Matcher matcher = pattern.matcher(weatherText);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "Temperature not found.";
        }
    }

    private String extractCondition(String weatherText) {
        String[] conditions = {"Sunny", "Rain", "Cloudy", "Snow", "Stormy"};
        for (String condition : conditions) {
            if (weatherText.contains(condition)) {
                return condition;
            }
        }
        return "Condition not found.";
    }
}
