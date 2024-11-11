package Splendor;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Game game;  // Reference to the Game instance

    public GamePanel(Game game) {
        this.game = game;  // Initialize with the Game instance
        setPreferredSize(new Dimension(1920, 1080)); // Set a preferred size
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Call the drawCards method of the Game instance to draw the cards
        game.drawCards(g, 0, 130, 160, 240); // Starting x, y, card width, card height, padding
        game.drawNobles(g, 680, 0, 100, 800);
        game.drawTokens(g, 0, 0, 100);
    }
}