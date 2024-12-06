package Splendor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap; 
public class EndPanel extends JPanel {
    BufferedImage background;
    private int[] scores = {32, 14, 18, 7}; //just testing
    private int[] players = {1, 2, 3, 4}; 
    public EndPanel() {
        this.background = Generator.loadImage("Splendor/assets/resultsScreen.PNG");  // Initialize with the Game instance
        //scores = GameState.getScore(); 
        int n = scores.length; 
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (scores[j] > scores[j + 1]) {
                  
                    int temp = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = temp;
                    
                    temp = players[j]; 
                    players[j] = players[j + 1]; 
                    players[j + 1] = temp; 
                }
        
        setPreferredSize(new Dimension(1920, 1080)); // Set a preferred size
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        //TODO implement showing what score is the highest
       
        g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 200));
        g.setColor(new Color(139, 69, 19));
        g.drawString("Player " + players[3] + " WINS! " , getWidth() / 100, getHeight() / 8 + 100);
        g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 75));
        g.drawString("Score: " + scores[3] , getWidth() / 100, getHeight() / 8 + 210);
        g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 70));
        g.drawString("Player " + players[2] + ": " + scores[2], getWidth() / 100 + 450, getHeight() / 8 + 210);
        g.drawString("Player " + players[1] + ": " + scores[1], getWidth() / 100 + 450, getHeight() / 8 + 290);
        g.drawString("Player " + players[0] + ": " + scores[0], getWidth() / 100 + 450, getHeight() / 8 + 370);
    }
}