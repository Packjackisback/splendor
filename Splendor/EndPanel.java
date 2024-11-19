package Splendor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndPanel extends JPanel {
    BufferedImage background;

    public EndPanel() {
        this.background = Generator.loadImage("Splendor/assets/resultsScreen.PNG");  // Initialize with the Game instance
        setPreferredSize(new Dimension(1920, 1080)); // Set a preferred size
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        //TODO implement showing what score is the highest
        GameState.getScore();
    }
}