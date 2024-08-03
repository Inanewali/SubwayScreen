package ca.ucalgary.edu.ensf380;

import ca.ucalgary.edu.ensf380.CombinedDisplay;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Subway Screen Application with CombinedDisplay integration.
 */
public class MyApp1 extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea outputTextArea;
    private JButton stopButton;
    private Process process;
    private CombinedDisplay combinedDisplay;
    private Timer timer;

    public MyApp1() {
        super("Subway Screen");

        // Set default close operation
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the text area to display the output
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        // Create the stop button
        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            if (process != null) {
                process.destroy();
                stopButton.setEnabled(false);
            }
        });

        // Initialize CombinedDisplay
        combinedDisplay = new CombinedDisplay();
        combinedDisplay.setPreferredSize(new Dimension(1200, 800));

        // Add the text area, button, and combined display to the content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.SOUTH);
        contentPane.add(stopButton, BorderLayout.NORTH);
        contentPane.add(combinedDisplay, BorderLayout.CENTER);
        setContentPane(contentPane);

        // Set the size and visibility of the frame
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        // Launch the executable jar file
        launchSimulator();
    }

    private void launchSimulator() {
        try {
            String[] command = {"java", "-jar", "./exe/SubwaySimulator.jar", "--in", "./data/subway.csv", "--out", "./out"};
            process = new ProcessBuilder(command).start();

            // Start a new thread to read the simulator output
            new Thread(() -> {
                try (InputStream inputStream = process.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        final String outputLine = line;
                        SwingUtilities.invokeLater(() -> outputTextArea.append(outputLine + "\n"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a shutdown hook to stop the process when the application is closed
        final Process finalProcess = process;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (finalProcess != null) {
                finalProcess.destroy();
            }
        }));

        // Keep the application alive for 5 minutes
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (finalProcess != null) {
                    finalProcess.destroy();
                }
                timer.cancel();
            }
        }, 5 * 60 * 1000); // 5 minutes in milliseconds
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyApp1::new);
    }
}
