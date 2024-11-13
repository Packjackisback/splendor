package Splendor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.*;

public class Game {
	private final HashMap<Token, Integer> tokenBank;
	private Stack<Card> greenCards;
	private Stack<Card> yellowCards;
	private Stack<Card> blueCards;
	private final ArrayList<Noble> nobleBank;
	private final ArrayList<Card> greenBoard;
	private final ArrayList<Card> yellowBoard;
	private final ArrayList<Card> blueBoard;
	private Card testingCard;
	private boolean firstCalculation;
	private int x, y, width, height;
	JPanel gamePanel;
	private Hand[] playerHands;

	
	public Game() throws IOException {
		playerHands = new Hand[4];
		for(int i = 0; i < playerHands.length; i++) {
			playerHands[i]= new Hand(i);
		}
		firstCalculation = true;
		blueCards = new Stack<Card>();
		yellowCards = new Stack<Card>();
		greenCards = new Stack<Card>();
		
		greenBoard = new ArrayList<Card>();
		blueBoard = new ArrayList<Card>();
		yellowBoard = new ArrayList<Card>();

		tokenBank = new HashMap<Token, Integer>();
		nobleBank = new ArrayList<Noble>();

		tokenBank.put(new Token( new Gem("Wild")), 5);
		tokenBank.put(new Token( new Gem("Red")), 7);
		tokenBank.put(new Token( new Gem("Green")), 7);
		tokenBank.put(new Token( new Gem("Blue")), 7);
		tokenBank.put(new Token( new Gem("Black")), 7);
		tokenBank.put(new Token( new Gem("White")), 7);

		Stack[] cardStacks = Generator.getCards(); //Generate cards and shuffle

		blueCards = cardStacks[0];
		Collections.shuffle(blueCards);
		yellowCards = cardStacks[1];
		Collections.shuffle(yellowCards);
		greenCards = cardStacks[2];
		Collections.shuffle(greenCards);
		dealCards();
		
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
	public HashMap<Token, Integer> getTokens() { return tokenBank; }
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
        
        double nobleWidth = frameWidth / 30.0;
        double nobleSpacing = nobleWidth * 0.3;
        
        width = (int) (frameWidth * .45);
        height = (int) (frameHeight * .5);
        
        x = (int)(xCenter - width/2);
        y = (int)(yCenter - height/2);
        
        // Calculating the coords for the draw cards
        blueCards.peek().setWidth((int)cardWidth); blueCards.peek().setHeight((int)cardHeight);
        yellowCards.peek().setWidth((int)cardWidth); yellowCards.peek().setHeight((int)cardHeight);
        greenCards.peek().setWidth((int)cardWidth); greenCards.peek().setHeight((int)cardHeight);
        
        int drawCardsXOffset = (int)(x + (cardSpacingX * 4));
        blueCards.peek().setX(drawCardsXOffset); blueCards.peek().setY((int)(y + cardSpacingY * 4));
        yellowCards.peek().setX(drawCardsXOffset); yellowCards.peek().setY((int)(blueCards.peek().getY() + cardSpacingY + cardHeight));
        greenCards.peek().setX(drawCardsXOffset); greenCards.peek().setY((int)(yellowCards.peek().getY() + cardSpacingY + cardHeight));
        
        // Sets the initial coordinates of the board cards to the deck card stack
        if (firstCalculation) {
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
            				blueBoard.get(j-1).startAnimation(xOffset, initYBlue, 500, gamePanel);
            			}
            		case 1:
            			int initYYellow = yellowCards.peek().getY();
            			for (int j = 1; j <= yellowBoard.size(); j++) {
            				int xOffset = (int)(yellowCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
            				yellowBoard.get(j-1).setX(initX);
            				yellowBoard.get(j-1).setY(initYYellow);
            				yellowBoard.get(j-1).setWidth((int)cardWidth);
            				yellowBoard.get(j-1).setHeight((int)cardHeight);
            				yellowBoard.get(j-1).startAnimation(xOffset, initYYellow, 500, gamePanel);
            			}
            		case 2:
            			int initYGreen = greenCards.peek().getY();
            			for (int j = 1; j <= greenBoard.size(); j++) {
            				int xOffset = (int)(greenCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
            				greenBoard.get(j-1).setX(initX);
            				greenBoard.get(j-1).setY(initYGreen);
            				greenBoard.get(j-1).setWidth((int)cardWidth);
            				greenBoard.get(j-1).setHeight((int)cardHeight);
            				greenBoard.get(j-1).startAnimation(xOffset, initYGreen, 500, gamePanel);
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
        				blueBoard.get(j-1).setX(xOffset);
        				blueBoard.get(j-1).setY(yOffsetBlue);
        				blueBoard.get(j-1).setWidth((int)cardWidth);
        				blueBoard.get(j-1).setHeight((int)cardHeight);
        			}
        		case 1:
        			int yOffsetYellow = yellowCards.peek().getY();
        			for (int j = 1; j <= yellowBoard.size(); j++) {
        				int xOffset = (int)(yellowCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
        				yellowBoard.get(j-1).setX(xOffset);
        				yellowBoard.get(j-1).setY(yOffsetYellow);
        				yellowBoard.get(j-1).setWidth((int)cardWidth);
        				yellowBoard.get(j-1).setHeight((int)cardHeight);
        			}
        		case 2:
        			int yOffsetGreen = greenCards.peek().getY();
        			for (int j = 1; j <= greenBoard.size(); j++) {
        				int xOffset = (int)(greenCards.peek().getX() + (cardWidth + cardSpacingX * 2.5) * j);
        				greenBoard.get(j-1).setX(xOffset);
        				greenBoard.get(j-1).setY(yOffsetGreen);
        				greenBoard.get(j-1).setWidth((int)cardWidth);
        				greenBoard.get(j-1).setHeight((int)cardHeight);
        			}
        		default: break;
        	}
        }
        
        // Calculating coords for the tokens
        for (int i = 0; i < tokenBank.size(); i++) {
        	for (int j = 0; j < tokenBank.get(new ArrayList(tokenBank.keySet()).get(i)); j++) {
        		Token t = (Token) new ArrayList(tokenBank.keySet()).get(i);

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
        	int xOffset = (int)(x + cardWidth * 8.5);
        	int yOffset = (int)((y + (cardSpacingY * 1.25)) + ((nobleWidth + nobleSpacing) * i));
        	nobleBank.get(i).setX(xOffset);
        	nobleBank.get(i).setY(yOffset);
        	nobleBank.get(i).setWidth((int)nobleWidth); nobleBank.get(i).setHeight((int)nobleWidth);
        }
        
        firstCalculation = false;
        //for (int i = 0; i < tokenBank.size(); i++) {
        //	for (int j = 0; j < tokenBank.get(i).size(); j++) {
        //		Token t = tokenBank.get(i).get(j);
        //		int xOffset = (int)(blueBoard.get(0).getX() + (i * (cardSpacingX * 5.0)));
        //		int yOffset = (int)(y + (cardSpacingY * 2));
        //		t.setX(xOffset);
        //		t.setY(yOffset);
        //		t.setWidth((int)chipRadius);
        //		t.setHeight((int)chipRadius);
        //	}
        //}
	}
	//
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
			greenCards.peek().flip();
			greenBoard.add(greenCards.pop());
		}
		while(yellowBoard.size()<4) {
			yellowCards.peek().flip();
			yellowBoard.add(yellowCards.pop());
		}
		while(blueBoard.size()<4) {
			blueCards.peek().flip();
			blueBoard.add(blueCards.pop());
		}
	}

	//If we need to display a message, like for an incorrect move or asking if you want to use a wild
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
		for(Token t : tokenBank.keySet()) {
			//System.out.println(tokenBank.get(t));
			g.drawString("" + tokenBank.get(t), t.getX(), t.getY());
			t.draw(g);
		}
	}
}