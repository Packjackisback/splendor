package Splendor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class GamePanel extends JPanel {
    private final Game game;  // Reference to the Game instance
    private int elapsedTime;
    private Timer animationTimer;
    private boolean currentAnimation;
    private EndPanel endPanel;
    private GameFrame frame;
    private BufferedImage background;
    private Hand testHand0, testHand1, testHand2, testHand3;
    
    public GamePanel(Game game, EndPanel endPanel, GameFrame gameFrame) {
        this.game = game;  // Initialize with the Game instance
        this.endPanel = endPanel;
        this.frame = gameFrame;
		
		// Testing the hands *** //
        testHand0 = new Hand(0, game);
		testHand2 = new Hand(2, game);
		testHand1 = new Hand(1, game);
		testHand3 = new Hand(3, game);
		
		testHand0.addCard(new Card("Splendor/assets/Cards/01.jpg", new Gem("White"), 0, new HashMap<Gem, Integer>(), 1));
		testHand0.addCard(new Card("Splendor/assets/Cards/010.jpg", new Gem("Black"), 0, new HashMap<Gem, Integer>(), 1));
		testHand0.addCard(new Card("Splendor/assets/Cards/013.jpg", new Gem("Green"), 0, new HashMap<Gem, Integer>(), 1));
		testHand0.addCard(new Card("Splendor/assets/Cards/018.jpg", new Gem("Green"), 0, new HashMap<Gem, Integer>(), 1));
		testHand0.addToken(new Token(new Gem("White")));
		testHand0.addToken(new Token(new Gem("Red")));
		testHand0.addToken(new Token(new Gem("Green")));
		
		testHand2.addCard(new Card("Splendor/assets/Cards/01.jpg", new Gem("White"), 0, new HashMap<Gem, Integer>(), 1));
		testHand2.addCard(new Card("Splendor/assets/Cards/010.jpg", new Gem("Black"), 0, new HashMap<Gem, Integer>(), 1));
		testHand2.addCard(new Card("Splendor/assets/Cards/013.jpg", new Gem("Green"), 0, new HashMap<Gem, Integer>(), 1));
		testHand2.addToken(new Token(new Gem("White")));
		testHand2.addToken(new Token(new Gem("Red")));
		
		testHand1.addCard(new Card("Splendor/assets/Cards/01.jpg", new Gem("White"), 0, new HashMap<Gem, Integer>(), 1));
		testHand1.addCard(new Card("Splendor/assets/Cards/01.jpg", new Gem("White"), 0, new HashMap<Gem, Integer>(), 1));
		testHand1.addCard(new Card("Splendor/assets/Cards/01.jpg", new Gem("White"), 0, new HashMap<Gem, Integer>(), 1));
		testHand1.addCard(new Card("Splendor/assets/Cards/010.jpg", new Gem("Black"), 0, new HashMap<Gem, Integer>(), 1));
		testHand1.addCard(new Card("Splendor/assets/Cards/010.jpg", new Gem("Black"), 0, new HashMap<Gem, Integer>(), 1));
		testHand1.addCard(new Card("Splendor/assets/Cards/013.jpg", new Gem("Green"), 0, new HashMap<Gem, Integer>(), 1));
		testHand1.addCard(new Card("Splendor/assets/Cards/013.jpg", new Gem("Green"), 0, new HashMap<Gem, Integer>(), 1));
		
		testHand3.addCard(new Card("Splendor/assets/Cards/01.jpg", new Gem("White"), 0, new HashMap<Gem, Integer>(), 1));
		testHand3.addCard(new Card("Splendor/assets/Cards/01.jpg", new Gem("White"), 0, new HashMap<Gem, Integer>(), 1));
		testHand3.addCard(new Card("Splendor/assets/Cards/01.jpg", new Gem("White"), 0, new HashMap<Gem, Integer>(), 1));
		testHand3.addCard(new Card("Splendor/assets/Cards/010.jpg", new Gem("Black"), 0, new HashMap<Gem, Integer>(), 1));
		testHand3.addCard(new Card("Splendor/assets/Cards/010.jpg", new Gem("Black"), 0, new HashMap<Gem, Integer>(), 1));
		testHand3.addCard(new Card("Splendor/assets/Cards/013.jpg", new Gem("Green"), 0, new HashMap<Gem, Integer>(), 1));
		testHand3.addCard(new Card("Splendor/assets/Cards/013.jpg", new Gem("Green"), 0, new HashMap<Gem, Integer>(), 1));
		testHand3.addCard(new Card("Splendor/assets/Cards/012.jpg", new Gem("Blue"), 0, new HashMap<Gem, Integer>(), 1));
		testHand3.addCard(new Card("Splendor/assets/Cards/012.jpg", new Gem("Blue"), 0, new HashMap<Gem, Integer>(), 1));
		
		// *** //
		
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
        testHand0.calculateCoords(getWidth(), getHeight());
        testHand1.calculateCoords(getWidth(), getHeight());
        testHand2.calculateCoords(getWidth(), getHeight());
        testHand3.calculateCoords(getWidth(), getHeight());
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        g.setColor(new Color(255, 255, 255, 127));
        g.fillRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2);
        game.drawCards(g); // Starting x, y, card width, card height, padding
        game.drawNobles(g);
        game.drawTokens(g);

        if (game.isInAnimation() && currentAnimation == false) {
            animateCards(500, g);
        }
        
        // Testing the Hands ** //
        g.setColor(Color.WHITE);
        g.drawString("Hand 0", testHand0.getX(), testHand0.getY());
        g.drawString("Hand 1", testHand1.getX(), testHand1.getY());
        g.drawString("Hand 2", testHand2.getX(), testHand2.getY());
        g.drawString("Hand 3", testHand3.getX(), testHand3.getY());
        TreeMap<Gem, ArrayList<Card>> cards = testHand0.getCards();
		TreeMap<Gem, ArrayList<Token>> tokens = testHand0.getTokens();
		for (Gem gem : cards.keySet()) {
			for (Card c : cards.get(gem)) {
				c.draw(g);
			}
		}
		
		for (Gem gem : tokens.keySet()) {
			for (Token t : tokens.get(gem)) {
				t.draw(g);
			}
		}
		
		cards = testHand2.getCards();
		tokens = testHand2.getTokens();
		for (Gem gem : cards.keySet()) {
			for (Card c : cards.get(gem)) {
				c.draw(g);
			}
		}
		
		for (Gem gem : tokens.keySet()) {
			for (Token t : tokens.get(gem)) {
				t.draw(g);
			}
		}
		
		cards = testHand1.getCards();
		//tokens = testHand1.getTokens();
		for (Gem gem : cards.keySet()) {
			for (Card c : cards.get(gem)) {
				c.draw(g);
			}
		}
		
		cards = testHand3.getCards();
		//tokens = testHand1.getTokens();
		for (Gem gem : cards.keySet()) {
			for (Card c : cards.get(gem)) {
				c.draw(g);
			}
		}
		// *** //
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