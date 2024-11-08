package Splendor;
import Splendor.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JPanel;

public class Game {
	JPanel gamePanel;
	ArrayList<ArrayList<Token>> tokenBank;
	Stack<Card> greenCards;
	Stack<Card> yellowCards;
	Stack<Card> blueCards;
	ArrayList<Noble> nobleBank = new ArrayList<Noble>();
	ArrayList<Card> greenBoard;
	ArrayList<Card> yellowBoard;
	ArrayList<Card> blueBoard;
	Card testingCard;
	int x, y, width, height;
	
	public Game() throws IOException {
		ArrayList<Noble> nobles = Generator.getNobles();
		Collections.shuffle(nobles);
		for(int i = 0; i<5; i++) this.nobleBank.add(nobles.get(i));
		greenBoard = new ArrayList<Card>();
		blueBoard = new ArrayList<Card>();
		yellowBoard = new ArrayList<Card>();
		
		tokenBank = new ArrayList<ArrayList<Token>>();
		
		for (int i = 0; i < 6; i++) {
			int count = 7;
			if (i == 0) { count = 5; }
			ArrayList<Token> tokens = new ArrayList<Token>();
			for (int j = 0; j < count; j++) {
				switch(i) {
					case 0: tokens.add(new Token(null, new Gem("Wild"))); break;
					case 1: tokens.add(new Token(null, new Gem("Red"))); break;
					case 2: tokens.add(new Token(null, new Gem("Blue"))); break;
					case 3: tokens.add(new Token(null, new Gem("Black"))); break;
					case 4: tokens.add(new Token(null, new Gem("White"))); break;
					case 5: tokens.add(new Token(null, new Gem("Green"))); break;
					default: break;
				}
			}
			tokenBank.add(tokens);
		}

		Stack[] cardStacks = Generator.getCards(); //Generate cards and shuffle

		blueCards = cardStacks[0];
		Collections.shuffle(blueCards);
		yellowCards = cardStacks[1];
		Collections.shuffle(yellowCards);
		greenCards = cardStacks[2];
		Collections.shuffle(greenCards);
		dealCards();

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
	public ArrayList<ArrayList<Token>> getTokens() { return tokenBank; }
	public ArrayList<Noble> getNobles() { return nobleBank; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }

	public void calculateCoords(int frameWidth, int frameHeight, double xCenter, double yCenter) {
		double cardWidth = frameWidth / 23.5;
        double cardHeight = frameHeight / 9.0;
        double cardSpacingX = cardWidth * 0.2; // 20%
        double cardSpacingY = cardHeight * 0.2;
        
        double chipRadius = frameWidth / 45.0;
        double chipSpacing = chipRadius * 0.4;
        
        double nobleWidth = frameWidth / 35.0;
        double nobleSpacing = nobleWidth * 0.3;
        
        width = (int) (frameWidth * .5);
        height = (int) (frameHeight * .5);
        
        x = (int)(xCenter - width/2);
        y = (int)(yCenter - height/2);
        
        // Calculating the coords for the draw cards
        blueCards.peek().setWidth((int)cardWidth); blueCards.peek().setHeight((int)cardHeight);
        yellowCards.peek().setWidth((int)cardWidth); yellowCards.peek().setHeight((int)cardHeight);
        greenCards.peek().setWidth((int)cardWidth); greenCards.peek().setHeight((int)cardHeight);
        
        int drawCardsXOffset = (int)(x + (cardSpacingX * 4));
        blueCards.peek().setX(drawCardsXOffset); blueCards.peek().setY((int)(y + cardSpacingY * 5));
        yellowCards.peek().setX(drawCardsXOffset); yellowCards.peek().setY((int)(blueCards.peek().getY() + cardSpacingY + cardHeight));
        greenCards.peek().setX(drawCardsXOffset); greenCards.peek().setY((int)(yellowCards.peek().getY() + cardSpacingY + cardHeight));
        
        
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
        	for (int j = 0; j < tokenBank.get(i).size(); j++) {
        		Token t = tokenBank.get(i).get(j);
        		int xOffset = (int)(blueBoard.get(0).getX() + (i * (cardSpacingX * 5.0)));
        		int yOffset = (int)(y + (cardSpacingY * 2));
        		t.setX(xOffset);
        		t.setY(yOffset);
        		t.setWidth((int)chipRadius);
        		t.setHeight((int)chipRadius);
        	}
        }
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

	public void dealCards() { //This doesn't deal 4 at once so it can be called after any card is drawn and not fuck everything up
		while(greenBoard.size()<4) {
			greenCards.peek().flip();
			greenBoard.add(0, greenCards.pop());
		}
		while(yellowBoard.size()<4) {
			yellowCards.peek().flip();
			yellowBoard.add(0, yellowCards.pop());
		}
		while(blueBoard.size()<4) {
			blueCards.peek().flip();
			blueBoard.add(0, blueCards.pop());
		}
	}


	// Draw methods
	public void drawNobles(Graphics g, int startX, int startY, int tokenBankWidth, int tokenBankHeight) {
		int availableSpaceY = tokenBankHeight/6 - 30; //Available space for each token minus total padding (5*6)
		int currentY = startY;

		for(Noble n : nobleBank) {
			n.draw(g, startX, currentY, tokenBankWidth, availableSpaceY);
			currentY += availableSpaceY + 5;
		}
	}

	public void drawCards(Graphics g, int startX, int startY, int cardWidth, int cardHeight) {
		int padding = 5; //Change to affect padding
		System.out.println("This has been called");
		int currentX = 0, currentY = 0;
		for(Card c : greenBoard) {
			c.draw(g, currentX, currentY, cardWidth, cardHeight);
			currentX += cardWidth + padding;
		}
		currentY += cardHeight + padding;
		currentX = startX;
		for(Card c : yellowBoard) {
			c.draw(g, currentX, currentY, cardWidth, cardHeight);
			currentX += cardWidth + padding;
		}
		currentY += cardHeight + padding;
		currentX = startX;
		for(Card c : blueBoard) {
			c.draw(g, currentX, currentY, cardWidth, cardHeight);
			currentX += cardWidth + padding;
		}
	}
}
