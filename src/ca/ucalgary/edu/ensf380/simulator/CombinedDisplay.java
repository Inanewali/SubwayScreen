package ca.ucalgary.edu.ensf380.simulator;

import ca.ucalgary.edu.ensf380.Advertisement;
import ca.ucalgary.edu.ensf380.AdvertisementDAO;
import ca.ucalgary.edu.ensf380.NewsFetcher;
import ca.ucalgary.edu.ensf380.WeatherFetcher;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CombinedDisplay extends JFrame {
    private List<Advertisement> advertisements;
    private JPanel adPanel;
    private JLabel adImageLabel;
    private JLabel adTitleLabel;
    private JLabel temperatureLabel;
    private JLabel conditionLabel;
    private JTextPane newsTextPane;
    private JLabel breakingNewsLabel;
    private int currentAdIndex = 0;
    private Timer adTimer;
    private Timer mapTimer;
    private int adDisplayTime = 10000; // 10 seconds for ads
    private int mapDisplayTime = 5000; // 5 seconds for map
    private boolean showingAds = true;
    private JPanel simulationPanel;
    private JPanel mapPanel;
    private TrainStationDisplay stationDisplay;
    private JSplitPane mainSplitPane;
    private JSplitPane adNewsSplitPane;

    public CombinedDisplay() {
        AdvertisementDAO adDAO = new AdvertisementDAO();
        advertisements = adDAO.getAllAdvertisements();
        System.out.println("Advertisements fetched: " + advertisements.size());

        adImageLabel = new JLabel("", SwingConstants.CENTER);
        adTitleLabel = new JLabel("", SwingConstants.CENTER);
        adTitleLabel.setFont(new Font("Serif", Font.BOLD, 18));

        temperatureLabel = new JLabel("Temperature: ");
        conditionLabel = new JLabel("Condition: ");
        temperatureLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        conditionLabel.setFont(new Font("Serif", Font.PLAIN, 14));

        newsTextPane = new JTextPane();
        newsTextPane.setContentType("text/html");
        newsTextPane.setEditable(false);

        breakingNewsLabel = new JLabel("Breaking News", SwingConstants.CENTER);
        breakingNewsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        breakingNewsLabel.setForeground(Color.RED);

        setLayout(new BorderLayout());

        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS));
        weatherPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        weatherPanel.add(temperatureLabel);
        weatherPanel.add(conditionLabel);

        adPanel = new JPanel();
        adPanel.setLayout(new BorderLayout());
        adPanel.add(adImageLabel, BorderLayout.CENTER);
        adPanel.add(adTitleLabel, BorderLayout.SOUTH);

        // Initialize simulation panel
        simulationPanel = new JPanel();
        simulationPanel.setLayout(new BorderLayout());

        // Initialize station display
        stationDisplay = new TrainStationDisplay("data\\subway.csv");
        System.out.println("Station display initialized.");

        // Create the split pane for ads and simulation
        adNewsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, adPanel, stationDisplay);
        adNewsSplitPane.setDividerLocation(400);
        adNewsSplitPane.setResizeWeight(0.75); // More space to the ad and map display

        JPanel newsPanel = new JPanel(new BorderLayout());
        newsPanel.add(breakingNewsLabel, BorderLayout.NORTH);
        newsPanel.add(new JScrollPane(newsTextPane), BorderLayout.CENTER);

        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, weatherPanel, adNewsSplitPane);
        mainSplitPane.setDividerLocation(150);
        mainSplitPane.setResizeWeight(0.2);

        JSplitPane newsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainSplitPane, newsPanel);
        newsSplitPane.setDividerLocation(600); // Less space to the news panel
        newsSplitPane.setResizeWeight(0.8); // More weight to the mainSplitPane

        add(newsSplitPane, BorderLayout.CENTER);

        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize simulation
        initializeSimulation();

        // Schedule ad and map display timers
        adTimer = new Timer();
        mapTimer = new Timer();

        adTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    displayNextAd();
                    showingAds = true;
                    scheduleMapDisplay();
                });
            }
        }, 0, adDisplayTime + mapDisplayTime);

        fetchWeather();
        fetchNews();
        displayNextAd();
    }

    private void initializeSimulation() {
        // Load the subway.csv file
        String inputFilePath = "data\\subway.csv";
        System.out.println("Loading subway data from: " + inputFilePath);
        Map<String, List<Station>> subwayLines = SubwayParser.parseCSV(inputFilePath);
        if (subwayLines == null) {
            System.err.println("Failed to load subway lines.");
            return;
        }
        System.out.println("Subway lines loaded: " + subwayLines.size());

        List<Train> trains = TrainInitializer.initializeTrains(subwayLines);
        if (trains == null || trains.isEmpty()) {
            System.err.println("Failed to initialize trains.");
            return;
        }
        System.out.println("Trains initialized: " + trains.size());

        // Create the MapPanel for the simulation
        mapPanel = new MapPanel(subwayLines, trains);
        simulationPanel.add(mapPanel, BorderLayout.CENTER);
        System.out.println("Map panel added to simulation panel.");

        // Start the train simulator
        TrainSimulator simulator = new TrainSimulator(trains, subwayLines, (MapPanel) mapPanel);
        simulator.startSimulation();
        System.out.println("Simulation started.");
    }

    private void fetchWeather() {
        WeatherFetcher weatherFetcher = new WeatherFetcher();
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

    private void fetchNews() {
        NewsFetcher newsFetcher = new NewsFetcher();
        try {
            String[] newsHeadlines = newsFetcher.fetchNews();
            StringBuilder newsContent = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (String headline : newsHeadlines) {
                String timestamp = sdf.format(new Date());
                newsContent.append("<b>").append(headline).append("</b><br>")
                           .append("<i>").append(timestamp).append("</i><br><br>");
            }

            newsTextPane.setText("<html><body>" + newsContent.toString() + "</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
            newsTextPane.setText("<html><body>Error fetching news.</body></html>");
        }
    }

    private void displayNextAd() {
        if (advertisements.isEmpty()) {
            adImageLabel.setText("No advertisements to display");
            adTitleLabel.setText("");
            return;
        }

        Advertisement ad = advertisements.get(currentAdIndex);
        String imagePath = ad.getMediaPath();

        System.out.println("Displaying ad: " + ad.getTitle() + ", Image path: " + imagePath);
        if (new File(imagePath).exists()) {
            System.out.println("Image file exists: " + imagePath);
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image scaledImage = imageIcon.getImage().getScaledInstance(450, 300, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
            adImageLabel.setIcon(imageIcon);
            adImageLabel.setText("");
        } else {
            System.out.println("Image file not found: " + imagePath);
            adImageLabel.setText("Image not found: " + imagePath);
            adImageLabel.setIcon(null);
        }

        adTitleLabel.setText(ad.getTitle());

        currentAdIndex = (currentAdIndex + 1) % advertisements.size();

        adPanel.removeAll();
        adPanel.add(adImageLabel, BorderLayout.CENTER);
        adPanel.add(adTitleLabel, BorderLayout.SOUTH);
        adPanel.revalidate();
        adPanel.repaint();

        adNewsSplitPane.setTopComponent(adPanel); // Show adPanel during ad display
        adNewsSplitPane.setDividerLocation(0.75); // Ensure consistent divider location
    }

    private void displayMap() {
        System.out.println("Displaying map...");
        adNewsSplitPane.setTopComponent(simulationPanel);
        adNewsSplitPane.setDividerLocation(0.75); // Ensure consistent divider location
        simulationPanel.revalidate();
        simulationPanel.repaint();
        System.out.println("Map displayed.");
    }

    private void scheduleMapDisplay() {
        // Cancel any existing map timer
        if (mapTimer != null) {
            mapTimer.cancel();
        }

        mapTimer = new Timer();
        mapTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    System.out.println("Switching to next station...");
                    stationDisplay.nextStation();
                    adNewsSplitPane.setBottomComponent(stationDisplay);
                    displayMap(); // Ensure map is displayed after each ad
                });
            }
        }, mapDisplayTime);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CombinedDisplay().setVisible(true));
    }
}
