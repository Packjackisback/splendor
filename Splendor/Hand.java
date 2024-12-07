package Splendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class Hand {
    //private final HashMap<Gem, Integer> credits;
    private TreeMap<Gem, ArrayList<Token>> tokens;
    private TreeMap<Gem, ArrayList<Card>> cards;
    private int playerNum;
    private ArrayList<Noble> nobles;
    private ArrayList<Card> reservedCards;
    private ArrayList<Token> reservedTokens;
    private Game game;
    private int x, y, width, height;
    private String playerName;

    public Hand(int num, Game game) {
        this.game = game;
        playerNum = num;
        tokens = new TreeMap<Gem, ArrayList<Token>>();
        cards = new TreeMap<Gem, ArrayList<Card>>();
        nobles = new ArrayList<Noble>();
        reservedCards = new ArrayList<Card>();
        reservedTokens = new ArrayList<Token>();
        playerName = "Player " + (num-1);
        //credits = new HashMap<Gem, Integer>();
    }

    // Getters
    public TreeMap<Gem, ArrayList<Token>> getTokens() { return tokens; }
    public TreeMap<Gem, ArrayList<Card>> getCards() { return cards; }
    public int getPlayerNum() { return playerNum; }
    public ArrayList<Noble> getNobles() { return nobles; }
    public int getScore() { 
      int score = 0;
      for(Gem g : cards.keySet()) {
        ArrayList<Card> cardset = cards.get(g);
        for(Card c : cardset) {
          score+=c.getWorth();
        }
      }
      for(Noble n : nobles) {
        score += n.getWorth();
      }
      return score;
    }
    public ArrayList<Card> getReservedCards() { return reservedCards; }
    public ArrayList<Token> getReservedTokens() { return reservedTokens; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void changePlayerNum(int newNum) {
        playerNum = newNum;
    }

	public HashMap<Gem, Integer> canAfford(Card x) {
		HashMap<Gem, Integer> costs = x.getCost();
		HashMap<Gem, Integer> tokensToRemove = new HashMap<Gem, Integer>();
		boolean needsToUseWilds = false;
		TreeMap<Gem, Integer> tokensAmts = new TreeMap<Gem, Integer>();
		for (Gem g : tokens.keySet()) {
			tokensAmts.put(g, 0);
			int count = 1;
			for (Token t : tokens.get(g)) {
				tokensAmts.put(g, count);
				count++;
			}
		}
		
		for (Gem g : costs.keySet()) {
			int cost = costs.get(g);
			
			if (cards.containsKey(g)) {
				cost -= cards.get(g).size();
			}
			
			int handTokenAmt = tokensAmts.containsKey(g) ? tokensAmts.get(g) : 0;
			if (cost <= 0) {
				
			} else if (cost > handTokenAmt) {
				needsToUseWilds = true;
				
				if (tokensAmts.containsKey(new Gem("Wild")) && tokensAmts.get(new Gem("Wild")) >= (cost - handTokenAmt)) {
					tokensToRemove.put(g, handTokenAmt);
					tokensAmts.put(g, 0);
					if (!tokensToRemove.containsKey(new Gem("Wild"))) {
						tokensToRemove.put(new Gem("Wild"), cost-handTokenAmt);
					} else {
						int amt = tokensToRemove.get(new Gem("Wild"));
						tokensToRemove.put(new Gem("Wild"), (amt + (cost - handTokenAmt)));
					}
					int wildTokenAmt = tokensAmts.get(new Gem("Wild"));
					tokensAmts.put(new Gem("Wild"), wildTokenAmt-(cost-handTokenAmt));
				} else {
					return null;
				}
			} else {
				System.out.println("Has enough tokens to afford card");
				System.out.println(g.getGemType() + ": " + cost);
				int amt = tokensAmts.get(g);
				tokensAmts.put(g, amt-cost);
				tokensToRemove.put(g, cost);
			}
		}
		
		if (needsToUseWilds) {
			final boolean[] useWild = {false};
	        Runnable doYouWantAWild = new Runnable() {
	          public void run() {
	            useWild[0] = true;
	            System.out.println("Using wild");
	          }
	        };
	        Game.showToast("Do you want to use your wilds?", "Use your wild(s)?","Yes", doYouWantAWild);
	        if (useWild[0]) {
	        	return tokensToRemove;
	        } else {
	        	return null;
	        }
		}
		System.out.println(tokensToRemove);
		return tokensToRemove;
	}
	
	public boolean reserveCheck() {
		if (reservedCards.size() >= 3) {
			Game.showToast("Cannot afford card, too many cards reserved", "Alert!", "Pick again", new Runnable() {
				public void run() {
					return;
				}
			});
			return false;
		}
		
		final boolean[] reserveCard = { false };
		Runnable doYouWantToReserve = new Runnable() {
			public void run() {
				reserveCard[0] = true;
				System.out.println("Reserving Card");
			}
		};
		
		if (game.containsWildToken()) {
			Game.showToast("Cannot afford card, reserve?", "Reserve?", "Yes", doYouWantToReserve);
		} else {
			Game.showToast("Cannot afford card, reserve?", "Reserve? (no wilds)", "Yes", doYouWantToReserve);
		}

		if (reserveCard[0]) {
			return true;
		}
		
		return false;
	}
	
    public boolean canAffordNoble(Noble x) {
        boolean check = false;
        for(Gem g : x.getCost().keySet()) {
            if(cards.get(g).size()<x.getCost().get(g)) {
                check=false;
            }
        }
        return check;
    }

    public void addCard(Card c) {
    	c.flip(); // Only doing this for testing rn
    	if (cards.get(c.getGem()) == null) {
    		cards.put(c.getGem(), new ArrayList<Card>());
    	}
    	cards.get(c.getGem()).add(c);
    }
    
    public void addToken(Token t) {
    	if (tokens.get(t.getGem()) == null) {
	    	tokens.put(t.getGem(), new ArrayList<Token>());
    	}
    	tokens.get(t.getGem()).add(t);
    }
    
    public void addNoble(Noble n) {
        nobles.add(n);
    }

    public void removeToken(Token t) {
    	if (!tokens.containsKey(t.getGem()) && tokens.containsKey(new Gem("Wild"))) {
    		tokens.get(new Gem("Wild")).remove(0);
    		return;
    	}
    	
		tokens.get(t.getGem()).remove(t);
		
		if(tokens.get(t.getGem()).size() == 0) {
			tokens.remove(t.getGem());
		}
	}
    
	public void calculateCoords(int frameWidth, int frameHeight) {
		boolean isHorizontal = playerNum % 2 == 0; // Player nums are: 0, 1, 2, 3 | 1 and 3 are vertical on the sides
		double cardWidth = frameWidth / 23.5;
		double cardHeight = frameHeight / 9.0;
		double cardSpacingX = cardWidth * 0.2; // 20%
		double cardSpacingY = cardHeight * 0.2;

		double chipRadius = frameWidth / 45.0;
		double chipSpacing = chipRadius * 0.4;

		double nobleWidth = frameWidth / 25.0;
		double nobleSpacing = nobleWidth * 0.3;

		int amtOfCardStacks = 0;
		for (Gem g : cards.keySet()) {
			if (cards.get(g).size() > 0) {
				amtOfCardStacks += Math.ceil((double)cards.get(g).size() / 3.0);
			}
		}

		if (isHorizontal) {
			width = (int) ((cardWidth * amtOfCardStacks) + (cardSpacingX * (amtOfCardStacks - 1)));
			height = (int) (cardHeight + cardSpacingY);

			if (playerNum == 0) { // At the bottom of the screen
				x = (int) (frameWidth / 2 - width / 2);
				y = (int) (frameHeight / 2 + game.getHeight() / 2 + cardSpacingY * 2);
			} else { // At the top of the screen
				x = (int) (frameWidth / 2 - width / 2);
				y = (int) (frameHeight / 2 - game.getHeight() / 2 - height - cardSpacingY * 3);
			}
			
			// Calculating the cards coordinates
			int count = 0;
			for (Gem g : cards.keySet()) {
				ArrayList<Card> c = cards.get(g);
				int yOffsetCount = 0;
				int xOffsetCount = 0;
				for (Card card : c) {
					card.setWidth((int) cardWidth);
					card.setHeight((int) cardHeight);
					card.setX((int) (x + (cardSpacingX + cardWidth) * count));
					card.setY((int) (y + (cardSpacingY + cardHeight / 13) * yOffsetCount));
					yOffsetCount++;
					xOffsetCount++;
					
					if (xOffsetCount == 3) {
						count++;
						yOffsetCount = 0;
					}
				}
				count++;
			}

			int yOutlierOffset = 0;
			int xOutlierCountTokens = 0;

			// Calculating the token coordinates
			for (Gem g : tokens.keySet()) {
				Card correspondingCard = null;
				if (cards.get(g) != null) {
					ArrayList<Card> c = cards.get(g);
					correspondingCard = c.get(c.size() - 1);
				}

				for (Token t : tokens.get(g)) {
					t.setWidth((int) chipRadius);
					t.setHeight((int) chipRadius);
					if (correspondingCard == null) {
						t.setX((int) (x + width + cardSpacingX + (chipRadius + cardSpacingX) * (xOutlierCountTokens / 2)));
						t.setY((int) (y + (yOutlierOffset * chipSpacing * 3)));
					} else {
						t.setX((int) (correspondingCard.getX() + correspondingCard.getWidth() - chipRadius));
						t.setY((int) (correspondingCard.getY() + correspondingCard.getHeight() - chipRadius));
					}
				}

				if (correspondingCard == null) {
					xOutlierCountTokens++;
					switch (yOutlierOffset) {
					case 0:
						yOutlierOffset = 1;
						break;
					case 1:
						yOutlierOffset = 0;
						break;
					default:
						break;
					}
				}
			}
			
			yOutlierOffset = 0; // YEAT IS DOOKIE
			int xOutlierCount = 0;
			for (Noble n : nobles) {
				n.setWidth((int) nobleWidth);
				n.setHeight((int) nobleWidth);

				n.setX((int) (x - nobleWidth - nobleSpacing - (nobleWidth + nobleSpacing) * (xOutlierCount/2)));
				n.setY((int)(y + (nobleWidth + nobleSpacing) * yOutlierOffset));

				xOutlierCount++;
				switch (yOutlierOffset) {
					case 0: yOutlierOffset = 1; break;
					case 1: yOutlierOffset = 0; break;
					default: break;
				}
			}
			
			// Calculating the reserved cards and tokens
			int reservedCardsOffset = 0;
			for (Card c : reservedCards) {
				int xOffset = (int) (x + width + cardSpacingX
						+ (cardWidth + cardSpacingX) * (reservedCardsOffset) + (chipRadius + chipSpacing) * (xOutlierCountTokens / 2));
				
				if (xOutlierCountTokens % 2 != 0) {
					xOffset += (int) (chipRadius + chipSpacing);
				}
				
				c.setWidth((int) cardWidth);
				c.setHeight((int) cardHeight);
				c.setX(xOffset);
				c.setY(y);
				
				if (reservedCardsOffset < reservedTokens.size()) {
					Token t = reservedTokens.get(reservedCardsOffset);
					
					if (t !=  null) {
						t.setWidth((int) chipRadius);
						t.setHeight((int) chipRadius);
						t.setX((int) (c.getX() + c.getWidth()/2 - chipRadius/2));
						t.setY((int) (c.getY() + c.getHeight()/2 - chipRadius/2));
					}
				}
				reservedCardsOffset++;
			}
		} else { // Is vertically positioned
			if (amtOfCardStacks == 1) {
				width = (int) (cardWidth + cardSpacingX);
			} else if (amtOfCardStacks == 2) {
				width = (int) (cardWidth * 2 + cardSpacingX);
			}
			height = (int) (Math.ceil((double) amtOfCardStacks / 2) * (cardHeight + cardSpacingY));

			if (playerNum == 1) { // On the left side of the screen
				x = (int) (game.getX() - (cardSpacingX * 25));
				y = (int) (game.getY() + cardSpacingY * 4);
			} else { // On the right side
				x = (int) (game.getX() + game.getWidth() + (cardSpacingX * 15));
				y = (int) (game.getY() + cardSpacingY * 4);
			}

			boolean needsOffsetForNobles = false; // Used to add to the x value of nobles if there is a token on the right

			int count = 0;
			int yOffset = 0;
			int maxAmtOfCards = 0;
			int highestYValue = 0;
			for (Gem g : cards.keySet()) { // Calculating cards
				ArrayList<Card> c = cards.get(g);
				int xOffset = count % 2 == 0 ? 0 : (int) (cardSpacingX + cardWidth);
				int cardsListYOffset = 0;

				if (tokens.containsKey(g)) {
					for (Token t : tokens.get(g)) {
						t.setWidth((int) chipRadius);
						t.setHeight((int) chipRadius);
						if (count % 2 == 0) {
							t.setX((int) (x - chipRadius));
							t.setY(y + yOffset);
						} else {
							t.setX((int) (x + cardSpacingX + cardWidth + cardWidth));
							t.setY(y + yOffset);
						}
					}

					if (count == 1 && playerNum == 3) {
						needsOffsetForNobles = true;
					} else if (count == 0 && playerNum == 1) {
						needsOffsetForNobles = true;
					}
				}

				for (Card card : c) {
					card.setWidth((int) cardWidth);
					card.setHeight((int) cardHeight);
					card.setX((int) (x + xOffset));
					card.setY((int) (y + yOffset + (cardSpacingY + cardHeight / 13) * cardsListYOffset));
					if (card.getY() > highestYValue) {
						highestYValue = card.getY();
					}
					cardsListYOffset++;
				}
				if (cardsListYOffset > maxAmtOfCards) {
					maxAmtOfCards = cardsListYOffset;
				}
				if (count % 2 == 1) {
					yOffset += (int) (cardHeight + cardSpacingY + (maxAmtOfCards - 1) * (cardSpacingY + cardHeight / 13));
					maxAmtOfCards = 0;
				}
				count++;
			}

			if (highestYValue != 0) {
				height = (int) (highestYValue - y + cardHeight);
			}

			int i = 0;
			int highestTokenY = Integer.MIN_VALUE; // Used for calculating reserved cards
			for (Gem g : tokens.keySet()) { // CALCULATING TOKENS
				if (!cards.containsKey(g)) {
					ArrayList<Token> t = tokens.get(g);
					for (Token token : t) {
						token.setWidth((int) chipRadius);
						token.setHeight((int) chipRadius);
						if (cards.keySet().size() == 1) {
							token.setX((int) ((i % 2) * (chipRadius + chipRadius / 7) + x));
							token.setY((int) (y + height + chipRadius / 2 + ((chipRadius + chipRadius / 2) * (i / 2))));
						} else { 
							token.setX((int) ((i % 3) * (chipRadius + chipRadius / 7) + x));
							token.setY((int) (y + height + chipRadius / 2 + ((chipRadius + chipRadius / 2) * (i / 3))));
						}
						
						if (token.getY() > highestTokenY) {
							highestTokenY = token.getY();
						}
					}
					i++;
				}
			}

			// Calculating coords for nobles
			int yOutlierOffset = 0;
			int xOutlierCount = 0;
			if (playerNum == 3) { // On the right side
				for (Noble n : nobles) {
					n.setWidth((int) nobleWidth);
					n.setHeight((int) nobleWidth);

					int nobleX = (int) (x + width + cardSpacingX);
					if (needsOffsetForNobles) {
						nobleX += (int) (chipRadius);
					}

					n.setX((int) (nobleX));
					n.setY((int) (y + (yOutlierOffset * nobleSpacing * 3.5)));

					xOutlierCount++;
					yOutlierOffset++;
				}
			} else { // On the left side
				for (Noble n : nobles) {
					n.setWidth((int) nobleWidth);
					n.setHeight((int) nobleWidth);

					int nobleX = x - (int) (cardSpacingX + nobleWidth);
					if (needsOffsetForNobles) {
						nobleX -= (int) (chipRadius);
					}

					n.setX(nobleX);
					n.setY((int) (y + (yOutlierOffset * nobleSpacing * 3.5)));

					xOutlierCount++;
					yOutlierOffset++;
				}
			}
			
			
			// Calculating coords for reserved cards
			int reservedCardsOffset = 0;
			if (playerNum == 1) {
				for (Card c : reservedCards) {
					int xReserved = (int)(x + width + cardSpacingX);
					int yReserved = (int)(y + (cardHeight + cardSpacingY) * reservedCardsOffset);
					
					if (cards.keySet().size() > 1 && tokens.keySet().size() > 1) {
						int reservedCount = 1;
						int tokenCount = 1;
						for (Gem g : cards.keySet()) {
							if (tokenCount == 2) {
								for (Gem g2 : tokens.keySet()) {
									if (tokenCount == 2 && g.equals(g2)) {
										xReserved += (int) (chipRadius + chipRadius / 7);
									}
									tokenCount++;
								}
							}
							reservedCount++;
						}
					} else if (cards.keySet().size() == 1 && tokens.keySet().size() == 1) {
						if (cards.keySet().iterator().next().equals(tokens.keySet().iterator().next())) {
							xReserved += (int) (chipRadius + chipRadius / 7);
						}
					}
					
					c.setWidth((int) cardWidth);
					c.setHeight((int) cardHeight);
					c.setX(xReserved);
					c.setY(yReserved);
	
					if (reservedCardsOffset < reservedTokens.size()) {
						Token t = reservedTokens.get(reservedCardsOffset);
						if (t != null) {
							t.setWidth((int) chipRadius);
							t.setHeight((int) chipRadius);
							t.setX((int) (c.getX() + c.getWidth()/2 - chipRadius/2));
							t.setY((int) (c.getY() + c.getHeight()/2 - chipRadius/2));
						}
					}
					reservedCardsOffset++;
				}
			} else if (playerNum == 3) {
				for (Card c : reservedCards) {
					int xReserved = (int)(x - cardSpacingX - cardWidth);
					int yReserved = (int)(y + (cardHeight + cardSpacingY) * reservedCardsOffset);
					
					if (cards.keySet().size() > 1 && tokens.keySet().size() > 1) {
						int reservedCount = 1;
						int tokenCount = 1;
						for (Gem g : cards.keySet()) {
							if (tokenCount == 2) {
								for (Gem g2 : tokens.keySet()) {
									if (tokenCount == 2 && g.equals(g2)) {
										xReserved -= (int) (chipRadius + chipRadius / 7);
									}
									tokenCount++;
								}
							}
							reservedCount++;
						}
					} else if (cards.keySet().size() == 1 && tokens.keySet().size() == 1) {
						if (cards.keySet().iterator().next().equals(tokens.keySet().iterator().next())) {
							xReserved -= (int) (chipRadius + chipRadius / 7);
						}
					}
					
					c.setWidth((int) cardWidth);
					c.setHeight((int) cardHeight);
					c.setX(xReserved);
					c.setY(yReserved);
	
					if (reservedCardsOffset < reservedTokens.size()) {
						Token t = reservedTokens.get(reservedCardsOffset);
						if (t != null) {
							t.setWidth((int) chipRadius);
							t.setHeight((int) chipRadius);
							t.setX((int) (c.getX() + c.getWidth()/2 - chipRadius/2));
							t.setY((int) (c.getY() + c.getHeight()/2 - chipRadius/2));
						}
					}
					reservedCardsOffset++;
				}
			}
		}
	}
	
	public void addReservedCard(Card c) {
		reservedCards.add(c);
		
		if (game.containsWildToken()) {
			reservedTokens.add(game.takeWildToken());
		} else {
			reservedTokens.add(null);
		}
	}
	
	public void removeReservedCard(Card c) {
		int index = reservedCards.indexOf(c);
		reservedCards.remove(index);

		if (reservedTokens.get(index) != null) {
			Token t = reservedTokens.remove(index);
			
			if (tokens.containsKey(new Gem("Wild"))) {
				tokens.get(new Gem("Wild")).add(t);
			} else {
				tokens.put(new Gem("Wild"), new ArrayList<Token>());
				tokens.get(new Gem("Wild")).add(t);
			}
		} else {
			reservedTokens.remove(index);
		}
	}
}
