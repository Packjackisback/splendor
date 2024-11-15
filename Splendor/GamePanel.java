package Splendor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class GamePanel extends JPanel {
    private final Game game;  // Reference to the Game instance
    private int elapsedTime;
    private Timer animationTimer;
    private boolean currentAnimation;
    private EndPanel endPanel;
    private GameFrame frame;
    private BufferedImage background;

    public GamePanel(Game game, EndPanel endPanel, GameFrame gameFrame) {
        this.game = game;  // Initialize with the Game instance
        this.endPanel = endPanel;
        this.frame = gameFrame;
        this.background = Generator.loadImage("Splendor/assets/gameBackground.png");
        game.setPanel(this);
        setPreferredSize(new Dimension(1920, 1080)); // Set a preferred size
        setVisible(true);
    }
    public void gameOver() {
        frame.setPanel(endPanel);
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

        if (game.isInAnimation() && currentAnimation == false) {
            animateCards(500, g);
        }
    }

    public void animateCards(int time, Graphics g) {
        ArrayList<ArrayList<Card>> boardCards = game.getCardArray();
        currentAnimation = true;
        animationTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int frameWidth = getWidth();
                int frameHeight = getHeight();
                elapsedTime += 16;
                double progress = (double) elapsedTime / time;

                if (progress >= 1.0) {
                    progress = 1.0;
                    for (ArrayList<Card> row : boardCards) {
                        for (Card c : row) {
                            c.setIsInAnimation(false);
                        }
                    }
                    currentAnimation = false;
                    animationTimer.stop();
                }

                for (ArrayList<Card> row : boardCards) {
                    for (Card c : row) {
                        HashMap<String, Double> animationValues = c.getAnimationValues();
                        double propOldX = animationValues.get("propOldX");
                        double propXDist = animationValues.get("propXDist");
                        double propOldY = animationValues.get("propOldY");
                        double propYDist = animationValues.get("propYDist");
                        c.setX((int) (frameWidth * (propOldX + propXDist * progress)));
                        c.setY((int) (frameHeight * (propOldY + propYDist * progress)));
                        //c.draw(g);
                        repaint();
                    }
                }
            }
        });
        animationTimer.start();
    }
}