package Splendor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private final Game game;  // Reference to the Game instance
    private EndPanel endPanel;
    private GameFrame frame;
    private BufferedImage background;
    public GamePanel(Game game, EndPanel endPanel, GameFrame gameFrame) {
        background = Generator.loadImage("Splendor/assets/gameBackground.png");
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
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        game.drawCards(g); // Starting x, y, card width, card height, padding
        game.drawNobles(g);
        game.drawTokens(g);
    }

    public void gameOver() {
        frame.setPanel(endPanel);
    }

}