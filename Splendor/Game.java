package Splendor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game {
	private final TreeMap<Gem, ArrayList<Token>> tokenBank;
	private Stack<Card> greenCards;
	private Stack<Card> yellowCards;
	private Stack<Card> blueCards;
	private final ArrayList<Noble> nobleBank;
	private final ArrayList<Card> greenBoard;
	private final ArrayList<Card> yellowBoard;
	private final ArrayList<Card> blueBoard;
	private boolean firstCalculation;
	private int x, y, width, height;
	JPanel gamePanel;

	
	public Game() throws IOException {
		firstCalculation = true;
		blueCards = new Stack<Card>();
		yellowCards = new Stack<Card>();
		greenCards = new Stack<Card>();
		
		greenBoard = new ArrayList<Card>();
		blueBoard = new ArrayList<Card>();
		yellowBoard = new ArrayList<Card>();

		tokenBank = new TreeMap<Gem, ArrayList<Token>>();
		nobleBank = new ArrayList<Noble>();

		tokenBank.put(new Gem("Wild"), new ArrayList<Token>());
		tokenBank.put(new Gem("Red"), new ArrayList<Token>());
		tokenBank.put(new Gem("Green"), new ArrayList<Token>());
		tokenBank.put(new Gem("Blue"), new ArrayList<Token>());
		tokenBank.put(new Gem("Black"), new ArrayList<Token>());
		tokenBank.put(new Gem("White"), new ArrayList<Token>());
		
		for (int i = 0; i < 5; i++) tokenBank.get(new Gem("Wild")).add(new Token(new Gem("Wild")));
		for (int i = 0; i < 7; i++) tokenBank.get(new Gem("Red")).add(new Token(new Gem("Red")));
		for (int i = 0; i < 7; i++) tokenBank.get(new Gem("Green")).add(new Token(new Gem("Green")));
		for (int i = 0; i < 7; i++) tokenBank.get(new Gem("Blue")).add(new Token(new Gem("Blue")));
		for (int i = 0; i < 7; i++) tokenBank.get(new Gem("Black")).add(new Token(new Gem("Black")));
		for (int i = 0; i < 7; i++) tokenBank.get(new Gem("White")).add(new Token(new Gem("White")));
		
		Stack[] cardStacks = Generator.getCards(); //Generate cards and shuffle

		blueCards = cardStacks[2];
		Collections.shuffle(blueCards);
		yellowCards = cardStacks[1];
		Collections.shuffle(yellowCards);
		greenCards = cardStacks[0];
		Collections.shuffle(greenCards);
		
		ArrayList<Noble> nobles = Generator.getNobles();
		Collections.shuffle(nobles);
		for (int i = 0; i < 5; i++) {
			nobleBank.add(nobles.get(i));
		}
	}

	public void setPanel(JPanel panel) {
		gamePanel = panel;
	}
	
	// Getters
	public ArrayList<ArrayList<Card>> getCardArray() {
		// Not sure what this is for, but this is in the UML
		ArrayList<ArrayList<Card>> out = new ArrayList<ArrayList<Card>>();
		out.add(greenBoard);
		out.add(yellowBoard);
		out.add(blueBoard);
		return out;
	}
	public ArrayList<Stack<Card>> getDrawCards() {
		ArrayList<Stack<Card>> cards = new ArrayList<Stack<Card>>();
		cards.add(blueCards);
		cards.add(yellowCards);
		cards.add(greenCards);
		return cards;
	}
	public TreeMap<Gem, ArrayList<Token>> getTokens() { return tokenBank; }
	public ArrayList<Noble> getNobles() { return nobleBank; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	//Animation methods
	public boolean isInAnimation() {
		boolean inAnimation = false;
		
		ArrayList<ArrayList<Card>> cards = getCardArray();
		for (int i = 0; i < cards.size(); i++) {
			for (int j = 0; j < cards.get(i).size(); j++) {
                if (cards.get(i).get(j).isInAnimation()) {
                    inAnimation = true;
                    break;
                }
			}
		}
		
		return inAnimation;
	}
	
	public void calculateCoords(int frameWidth, int frameHeight, double xCenter, double yCenter) {
		double cardWidth = frameWidth / 23.5;
        double cardHeight = frameHeight / 9.0;
        double cardSpacingX = cardWidth * 0.2; // 20%
        double cardSpacingY = cardHeight * 0.2;
        
        double chipRadius = frameWidth / 45.0;
        double chipSpacing = chipRadius * 0.4;
        
        double nobleWidth = frameWidth / 25.0;
        double nobleSpacing = nobleWidth * 0.3;
        
        width = (int) ((cardWidth + cardSpacingX * 2.5) * 5 + nobleWidth);
        height = (int) (nobleWidth * 5 + nobleSpacing * 4);
        
        x = (int)(xCenter - width/2);
        y = (int)(yCenter - height/2);
        
        // Calculating the coords for the draw cards
        int drawCardsXOffset = (int)(x);
        for (Card c : blueCards) {
        	c.setWidth((int)cardWidth);
        	c.setHeight((int)cardHeight);
        	c.setX(drawCardsXOffset);
        	c.setY((int)(y + cardSpacingY * 4));
        }
        for (Card c : yellowCards) {
        	c.setWidth((int)cardWidth);
        	c.setHeight((int)cardHeight);
        	c.setX(drawCardsXOffset);
        	c.setY((int)(blueCards.peek().getY() + cardSpacingY + cardHeight));
        }
        for (Card c : greenCards) {
        	c.setWidth((int)cardWidth);
        	c.setHeight((int)cardHeight);
        	c.setX(drawCardsXOffset);
        	c.setY((int)(yellowCards.peek().getY() + cardSpacingY + cardHeight));
        }
        // Sets the initial coordinates of the board cards to the deck card stack
        if (firstCalculation) {
        	dealCards();
        	int initX = blueCards.peek().getX();
        	for (int i = 0; i < 3; i++) {
            	switch (i) {
            		case 0:
            			int initYBlue = blueCards.peek().getY();
            			for (int j = 1; j <= blueBoard.size(); j++) {
            				int xOffset = (int)(blueCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
            				blueBoard.get(j-1).setX(initX);
            				blueBoard.get(j-1).setY(initYBlue);
            				blueBoard.get(j-1).setWidth((int)cardWidth);
            				blueBoard.get(j-1).setHeight((int)cardHeight);
            				blueBoard.get(j-1).startAnimation(xOffset, initYBlue, gamePanel);
            			}
            		case 1:
            			int initYYellow = yellowCards.peek().getY();
            			for (int j = 1; j <= yellowBoard.size(); j++) {
            				int xOffset = (int)(yellowCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
            				yellowBoard.get(j-1).setX(initX);
            				yellowBoard.get(j-1).setY(initYYellow);
            				yellowBoard.get(j-1).setWidth((int)cardWidth);
            				yellowBoard.get(j-1).setHeight((int)cardHeight);
            				yellowBoard.get(j-1).startAnimation(xOffset, initYYellow, gamePanel);
            			}
            		case 2:
            			int initYGreen = greenCards.peek().getY();
            			for (int j = 1; j <= greenBoard.size(); j++) {
            				int xOffset = (int)(greenCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
            				greenBoard.get(j-1).setX(initX);
            				greenBoard.get(j-1).setY(initYGreen);
            				greenBoard.get(j-1).setWidth((int)cardWidth);
            				greenBoard.get(j-1).setHeight((int)cardHeight);
            				greenBoard.get(j-1).startAnimation(xOffset, initYGreen, gamePanel);
            			}
            		default: break;
            	}
            }
        }
        
        // Calculating the coords for the cards on the board
        for (int i = 0; i < 3; i++) {
        	switch (i) {
        		case 0:	
        			int yOffsetBlue = blueCards.peek().getY();
        			for (int j = 1; j <= blueBoard.size(); j++) {
        				int xOffset = (int)(blueCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
        				if (!blueBoard.get(j-1).isInAnimation()) {
	        				blueBoard.get(j-1).setX(xOffset);
	        				blueBoard.get(j-1).setY(yOffsetBlue);
	        				blueBoard.get(j-1).setWidth((int)cardWidth);
	        				blueBoard.get(j-1).setHeight((int)cardHeight);
        				}
        			}
        		case 1:
        			int yOffsetYellow = yellowCards.peek().getY();
        			for (int j = 1; j <= yellowBoard.size(); j++) {
        				int xOffset = (int)(yellowCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
        				if (!yellowBoard.get(j-1).isInAnimation()) {
	        				yellowBoard.get(j-1).setX(xOffset);
	        				yellowBoard.get(j-1).setY(yOffsetYellow);
	        				yellowBoard.get(j-1).setWidth((int)cardWidth);
	        				yellowBoard.get(j-1).setHeight((int)cardHeight);
        				}
        			}
        		case 2:
        			int yOffsetGreen = greenCards.peek().getY();
        			for (int j = 1; j <= greenBoard.size(); j++) {
        				int xOffset = (int)(greenCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
        				if (!greenBoard.get(j-1).isInAnimation()) {
	        				greenBoard.get(j-1).setX(xOffset);
	        				greenBoard.get(j-1).setY(yOffsetGreen);
	        				greenBoard.get(j-1).setWidth((int)cardWidth);
	        				greenBoard.get(j-1).setHeight((int)cardHeight);
        				}
        			}
        		default: break;
        	}
        }
        
        // Calculating coords for the tokens
        for (int i = 0; i < tokenBank.size(); i++) {
        	Set<Gem> keys = tokenBank.keySet();
        	for (int j = 0; j < tokenBank.get(keys.toArray()[i]).size(); j++) {
        		Token t = tokenBank.get(keys.toArray()[i]).get(j);

        		int xOffset = (int)(blueCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) + ((cardWidth) * i));
        		int yOffset = (int)(y + (cardSpacingY * 1.25));
        		t.setX(xOffset);
        		t.setY(yOffset);
        		t.setWidth((int)chipRadius);
        		t.setHeight((int)chipRadius);
        	}
        }
        
        // Calculating coords for the nobles
        for (int i = 0; i < nobleBank.size(); i++) {
        	int xOffset = (int)(x + (cardWidth + cardSpacingX * 2.5) * 5);
        	int yOffset = (int)((y + (cardSpacingY * 1.25)) + ((nobleWidth + nobleSpacing) * i));
        	nobleBank.get(i).setX(xOffset);
        	nobleBank.get(i).setY(yOffset);
        	nobleBank.get(i).setWidth((int)nobleWidth); nobleBank.get(i).setHeight((int)nobleWidth);
        }
        
        firstCalculation = false;
	}
	
	public Noble takeNoble(Noble y) {
		Noble out = y;
		nobleBank.remove(y);
		return(out);
	}
	public void addToken(Token t) {
		if(tokenBank.containsKey(t.getGem())) {
			ArrayList<Token> tokenList = tokenBank.get(t.getGem());
			tokenList.add(t);
			return;
		}
		ArrayList<Token> tokenList = new ArrayList<>();
		tokenList.add(t);
		tokenBank.put(t.getGem(), tokenList);
	}
	public Token takeToken(Token t) {
		if(tokenBank.containsKey(t.getGem()) && tokenBank.get(t.getGem()).size() > 0) {
			tokenBank.get(t.getGem()).remove(tokenBank.get(t.getGem()).indexOf(t));
			gamePanel.revalidate();
			gamePanel.repaint();
			return(t);
		}
		return null;
	}

	public Card takeCardXY(int x, int y) {
		// This needs to be followed with a call to the class to redraw the cards
		Card out;
		switch (y) {
		case 0:
			out = greenBoard.remove(x);
			break;
		case 1:
			out = yellowBoard.remove(x);
			break;
		case 2:
			out = blueBoard.remove(x);
			break;
		default:
			throw new RuntimeException(
					"Input card y value is incorrect. Should be 0-2, but is " + y + "\nError in Game.drawCard");
		}
		dealCards();
		return out;
	}

//	public Card pickCard(Card c) {
//		Card out;
//		if (greenCards.contains(c)) {
//			greenCards.remove(c);
//		}
//		if (blueCards.contains(c)) {
//			blueBoard.remove(c);
//		}
//		if (yellowBoard.contains(c)) {
//			yellowBoard.remove(c);
//		}
//		return c;
//	}
	public Card takeCard(Card c) {
		//Drawing a card from the stacks
		if(greenBoard.contains(c)) {
			System.out.println("Removing card " + c + " from board");
			for(Card card : greenBoard) {
				System.out.println(card);
			}
			System.out.println("The current index of card " + c + " is " + greenBoard.indexOf(c));

			greenBoard.remove(greenBoard.indexOf(c));
			System.out.println("The new board is:");
			for(Card card : greenBoard) {
				System.out.println(card);
			}
			gamePanel.revalidate();
			gamePanel.repaint();
			dealCards();
			return c;
		}
		if(yellowBoard.contains(c)) {
			System.out.println("yellow");
			yellowBoard.remove(c);
			gamePanel.revalidate();
			gamePanel.repaint();
			dealCards();
			return c;
		}
		if(blueBoard.contains(c)) {
			System.out.println("blue");
			blueBoard.remove(c);
			gamePanel.revalidate();
			gamePanel.repaint();
			dealCards();
			return c;
		}

		System.out.println("Not found :(");
		return null;
	}
	
	public Card drawCard(int x, int y) {
		// This needs to be followed with a call to the class to redraw the cards
		Card out;
		switch (y) {
		case 0:
			out = greenBoard.remove(x);
			break;
		case 1:
			out = yellowBoard.remove(x);
			break;
		case 2:
			out = blueBoard.remove(x);
			break;
		default:
			throw new RuntimeException(
					"Input card y value is incorrect. Should be 0-2, but is " + y + "\nError in Game.drawCard");
		}
		dealCards();
		return out;
	}

	public void dealCards() {
		while(greenBoard.size()<4) {
			if(!greenCards.isEmpty()) {
				greenCards.peek().flip();
				greenBoard.add(greenCards.pop());
			}
		}
		while(yellowBoard.size()<4) {
			if(!yellowCards.isEmpty()) {
				yellowCards.peek().flip();
				yellowBoard.add(yellowCards.pop());
			}
		}
		while(blueBoard.size()<4) {
			if(!blueCards.isEmpty()) {
				blueCards.peek().flip();
				blueBoard.add(blueCards.pop());
			}
		}
	}

	// Draw methods
	public void drawNobles(Graphics g) {
		for(Noble n : nobleBank) {
			n.draw(g);
		}
	}

	public void drawCards(Graphics g) {
		for(Card c : greenBoard) {
			c.draw(g);
		}
		
		for(Card c : yellowBoard) {
			c.draw(g);
		}
		
		for(Card c : blueBoard) {
			c.draw(g);
		}
		
		blueCards.peek().draw(g);
		yellowCards.peek().draw(g);
		greenCards.peek().draw(g);
	}

	//DRAWING THE TEXT SCREEN
	public void drawTokens(Graphics g) {
		g.setColor(Color.BLACK);
		for (Gem gem : tokenBank.keySet()) {
			for (Token t : tokenBank.get(gem)) {
				g.drawString("" + tokenBank.get(gem).size(), t.getX(), t.getY());
				t.draw(g, tokenBank.get(gem).size());
			}
		}
	}
	
	// If we need to display a message, like for an incorrect move or asking if you
	// want to use a wild
	public static void showToast(String message, String title, String buttonText, Runnable buttonClickCallback) {
		JDialog toastDialog = new JDialog();
		toastDialog.setTitle(title);
		toastDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		toastDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		toastDialog.setSize(300, 150);
		toastDialog.setLocationRelativeTo(null);

		JPanel contentPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel messageLabel = new JLabel(message);
		messageLabel.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		contentPanel.add(messageLabel, gbc);

		JButton actionButton = new JButton(buttonText);
		actionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toastDialog.dispose();
				buttonClickCallback.run();
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 0.0;
		contentPanel.add(actionButton, gbc);

		toastDialog.setContentPane(contentPanel);
		toastDialog.setVisible(true);
	}
	public static void showDialog(JDialog dialog) {
		dialog.setVisible(true);
	}
}
