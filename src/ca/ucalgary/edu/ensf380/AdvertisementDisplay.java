package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class AdvertisementDisplay extends JFrame {
    private List<Advertisement> advertisements;
    private JLabel adLabel;
    private int currentAdIndex = 0;

    public AdvertisementDisplay() {
        AdvertisementDAO adDAO = new AdvertisementDAO();
        advertisements = adDAO.getAllAdvertisements();

        adLabel = new JLabel("", SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(adLabel, BorderLayout.CENTER);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayNextAd();
            }
        });
        timer.start();

        displayNextAd();
    }

    private void displayNextAd() {
        if (advertisements.isEmpty()) {
            adLabel.setText("No advertisements to display");
            return;
        }

        Advertisement ad = advertisements.get(currentAdIndex);
        String imagePath = ad.getMediaPath();

        // Check if the file exists
        if (new File(imagePath).exists()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            adLabel.setIcon(imageIcon);
            adLabel.setText(ad.getTitle());
        } else {
            adLabel.setText("Image not found: " + imagePath);
            adLabel.setIcon(null);
        }

        currentAdIndex = (currentAdIndex + 1) % advertisements.size();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdvertisementDisplay().setVisible(true);
            }
        });
    }
}
