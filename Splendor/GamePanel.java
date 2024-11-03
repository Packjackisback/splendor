package Splendor;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Game game;  // Reference to the Game instance

    public GamePanel(Game game) {
        this.game = game;  // Initialize with the Game instance
        setPreferredSize(new Dimension(800, 600)); // Set a preferred size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Call the drawCards method of the Game instance to draw the cards
        game.drawCards(g, 50, 50, 80, 120, 10); // Starting x, y, card width, card height, padding
    }
}