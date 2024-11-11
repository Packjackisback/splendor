import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JPanel;

public class Game {
<<<<<<< Updated upstream
	private JPanel gamePanel;
	private ArrayList<ArrayList<Token>> tokenBank;
	private Stack<Card> greenCards;
	private Stack<Card> yellowCards;
	private Stack<Card> blueCards;
	private ArrayList<Noble> nobleBank;
	private ArrayList<Card> greenBoard;
	private ArrayList<Card> yellowBoard;
	private ArrayList<Card> blueBoard;
	private Card testingCard;
	private boolean firstCalculation;
	private int x, y, width, height;
=======
	JPanel gamePanel;
	HashMap<Token, Integer> tokenBank;
	Stack<Card> greenCards;
	Stack<Card> yellowCards;
	Stack<Card> blueCards;
	ArrayList<Noble> nobleBank = new ArrayList<Noble>();
	ArrayList<Card> greenBoard;
	ArrayList<Card> yellowBoard;
	ArrayList<Card> blueBoard;
	Card testingCard;
	int x, y, width, height;
>>>>>>> Stashed changes
	
	public Game(JPanel panel) {
		//TODO implement reading from csv file
		//Stack[] cards = Generator.getCards();
		//this.greenCards = cards[0];
		//this.yellowCards = cards[1];
		//this.blueCards = cards[2];
		firstCalculation = true;
		gamePanel = panel;
		blueCards = new Stack<Card>();
		yellowCards = new Stack<Card>();
		greenCards = new Stack<Card>();
		
		greenBoard = new ArrayList<Card>();
		blueBoard = new ArrayList<Card>();
		yellowBoard = new ArrayList<Card>();
		
<<<<<<< Updated upstream
		tokenBank = new ArrayList<ArrayList<Token>>();
		nobleBank = new ArrayList<Noble>();
		
		for (int i = 0; i < 6; i++) {
			int count = 7;
			if (i == 0) { count = 5; }
			ArrayList<Token> tokens = new ArrayList<Token>();
			for (int j = 0; j < count; j++) {
				switch(i) {
					case 0: tokens.add(new Token(null, new Gem("Wild"), false)); break;
					case 1: tokens.add(new Token(null, new Gem("Ruby"), false)); break;
					case 2: tokens.add(new Token(null, new Gem("Sapphire"), false)); break;
					case 3: tokens.add(new Token(null, new Gem("Onyx"), false)); break;
					case 4: tokens.add(new Token(null, new Gem("Diamond"), false)); break;
					case 5: tokens.add(new Token(null, new Gem("Emerald"), false)); break;
					default: break;
				}
			}
			tokenBank.add(tokens);
		}
		blueCards.add(new Card());
		yellowCards.add(new Card());
		greenCards.add(new Card());
		
		for (int i = 0; i < 3; i++) {
			switch(i) {
			case 0: for (int j = 0; j < 4; j++) { blueBoard.add(new Card()); } break;
			case 1: for (int j = 0; j < 4; j++) { yellowBoard.add(new Card()); } break;
			case 2: for (int j = 0; j < 4; j++) { greenBoard.add(new Card()); } break;
			default: break;
			}
		}
		
		for (int i = 0; i < 5; i++) {
			nobleBank.add(new Noble(null));
		}
		//testingCard = new Card(Generator.loadImage("Splendor/assets/BlueCard.jpg"), new Gem("Blue"), 1, new HashMap(), 1);
=======
		tokenBank = new HashMap<Token, Integer>();
		
		tokenBank.put(new Token("Splendor/assets/tokens/GoldToken.jpg", new Gem("Wild")), 5);
		tokenBank.put(new Token("Splendor/assets/tokens/BlueToken.jpg", new Gem("Blue")), 7);
		tokenBank.put(new Token("Splendor/assets/tokens/BrownToken.jpg", new Gem("Brown")), 7);
		tokenBank.put(new Token("Splendor/assets/tokens/GreenToken.jpg", new Gem("Green")), 7);
		tokenBank.put(new Token("Splendor/assets/tokens/RedToken.jpg", new Gem("Red")), 7);
		tokenBank.put(new Token("Splendor/assets/tokens/WhiteToken.jpg", new Gem("White")), 7);

		Stack[] cardStacks = Generator.getCards(); //Generate cards and shuffle

		blueCards = cardStacks[0];
		Collections.shuffle(blueCards);
		yellowCards = cardStacks[1];
		Collections.shuffle(yellowCards);
		greenCards = cardStacks[2];
		Collections.shuffle(greenCards);
		dealCards();

>>>>>>> Stashed changes
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
	
	public boolean isInAnimation() {
		boolean inAnimation = false;
		
		ArrayList<ArrayList<Card>> cards = getCardArray();
		for (int i = 0; i < cards.size(); i++) {
			for (int j = 0; j < cards.get(i).size(); j++) {
				if (cards.get(i).get(j).isInAnimation()) {
					inAnimation = true;
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
<<<<<<< Updated upstream
        for (int i = 0; i < tokenBank.size(); i++) {
        	for (int j = 0; j < tokenBank.get(i).size(); j++) {
        		Token t = tokenBank.get(i).get(j);
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
=======
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
>>>>>>> Stashed changes
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
		for (int i = 0; i < 4; i++) {
			Card greenCard = greenCards.pop();
			Card yellowCard = yellowCards.pop();
			Card blueCard = blueCards.pop();
			greenCard.flip(); greenBoard.add(greenCard);
			yellowCard.flip(); yellowBoard.add(yellowCard);
			blueCard.flip(); blueBoard.add(blueCard);
			
			//greenCard.startAnimation(i, i, i, gamePanel);
		}
	}

	// Draw methods
	public void drawTokens(Graphics g, int startX, int startY, int tokenBankWidth, int tokenBankHeight) {

	}

	public void drawCards(Graphics g, int startX, int startY, int cardWidth, int cardHeight) {
<<<<<<< Updated upstream
		// also includes the
		testingCard.draw(g, startX, startY, cardWidth, cardHeight);
		// Above is just testing, eventually this will hold the Lists of Cards, and this
		// will display them
		// TODO Implement drawing 2d array of cards
=======
		int padding = 5; //Change to affect padding
		int currentX = startX, currentY = startY;
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
>>>>>>> Stashed changes
	}
//DRAWING THE TEXT SCREEN
	public void drawTokens(Graphics g, int startX, int startY, int tokenSize) {
		int currentX = startX;
		int imageY = startY + tokenSize/5; //segmenting 1/6 for the number of tokens
		g.setColor(Color.BLACK);
		for(Token t : tokenBank.keySet()) {
			System.out.println(tokenBank.get(t));
			g.drawString("" + tokenBank.get(t), currentX, startY);
			t.draw(g, currentX, imageY, tokenSize, tokenSize);
			currentX += tokenSize + 10;
		}
	}
}
