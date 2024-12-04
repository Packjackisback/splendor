package Splendor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap; 
public class EndPanel extends JPanel {
    BufferedImage background;
    private int[] scores; 
    public EndPanel() {
        this.background = Generator.loadImage("Splendor/assets/resultsScreen.PNG");  // Initialize with the Game instance
        scores = GameState.getScore(); 
        setPreferredSize(new Dimension(1920, 1080)); // Set a preferred size
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        //TODO implement showing what score is the highest
        g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
        g.setColor(new Color(139, 69, 19));
        g.drawString("Sigma Sigma Boy", getWidth() / 4, getHeight() / 4);
    }
}