package Splendor;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Game game;  // Reference to the Game instance
    private EndPanel endPanel;
    private GameFrame frame;
    public GamePanel(Game game, EndPanel endPanel, GameFrame gameFrame) {
        this.game = game;  // Initialize with the Game instance
        this.endPanel = endPanel;
        this.frame = gameFrame;
        game.setPanel(this);
        setPreferredSize(new Dimension(1920, 1080)); // Set a preferred size
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Call the drawCards method of the Game instance to draw the cards
        game.calculateCoords(getWidth(), getHeight(), getWidth()/2, getHeight()/2);
        game.drawCards(g); // Starting x, y, card width, card height, padding
        game.drawNobles(g);
        game.drawTokens(g);
    }

    public void gameOver() {
        frame.setPanel(endPanel);
    }
}