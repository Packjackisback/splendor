package Splendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class Hand {
    //private final HashMap<Gem, Integer> credits;
    private final TreeMap<Gem, ArrayList<Token>> tokens;
    private final TreeMap<Gem, ArrayList<Card>> cards;
    private int playerNum;
    private final ArrayList<Noble> nobles;
    private int score;
    private final ArrayList<Card> reservedCards;
    private Game game;
    private int x, y, width, height;
    private String playerName;

    public Hand(int num, Game game) {
        this.game = game;
        playerNum = num;
        score = 0;
        tokens = new TreeMap<Gem, ArrayList<Token>>();
        cards = new TreeMap<Gem, ArrayList<Card>>();
        nobles = new ArrayList<Noble>();
        reservedCards = new ArrayList<Card>();
        playerName = "Player " + (num-1);
        //credits = new HashMap<Gem, Integer>();
    }

    // Getters
    public TreeMap<Gem, ArrayList<Token>> getTokens() { return tokens; }
    public TreeMap<Gem, ArrayList<Card>> getCards() { return cards; }
    public int getPlayerNum() { return playerNum; }
    public ArrayList<Noble> getNobles() { return nobles; }
    public int getScore() { return score; }
    public ArrayList<Card> getReservedCards() { return reservedCards; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void changePlayerNum(int newNum) {
        playerNum = newNum;
    }

    public boolean canAfford(Card x) {
        HashMap<Gem, Integer> cost = x.getCost();
        boolean check = false;
        Set<Gem> keys = cost.keySet();
        Iterator<Gem> iter = keys.iterator();
        while (iter.hasNext()) {
            Gem gem = iter.next();
            if(!cards.containsKey(gem)) {return false;}
            int amt = cards.get(gem).size() + tokens.get(gem).size();

            if (amt >= cost.get(gem)) {
            	check = true;
            } else {
                //Implement asking for cards
                int wildsNeeded=cost.get(gem)-amt;
                if(tokens.get(new Gem("Wild")).size()>wildsNeeded) {
                    final boolean[] useWild = {false};
                    Runnable doYouWantAWild = new Runnable() {
                        public void run() {
                            useWild[0] = true;
                            System.out.println("Using wild");
                        }
                    };
                    Game.showToast("Do you want to use your " + wildsNeeded + " wilds?", "Use your wild(s)?","Yes",doYouWantAWild);
                    for(int i = 0; i<wildsNeeded; i++) {
                        tokens.get(new Gem("Wild")).remove(0);
                    }
                    break;
                }
            	check = false;
                break;
            }
        }
        return check;
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

    private int drawnTokens;
    public void drawTurn(Object o) {
        if(o.getClass().getSimpleName().equals("Noble")) {
            if(canAffordNoble((Noble)o)) {
                game.takeNoble((Noble) o);
                //TODO implement turn over
            }
        }
        if(o.getClass().getSimpleName().equals("Card")) {
            if(canAfford((Card) o)) {
                game.takeCard((Card)o);
                //TODO implement turn over
            }
        }
        if(o.getClass().getSimpleName().equals("Token")) {
             //passing to another method because it is gonna be complex
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
        
        double nobleWidth = frameWidth / 30.0;
        double nobleSpacing = nobleWidth * 0.3;
    	
        int amtOfCardStacks = 0;
        for (Gem g : cards.keySet()) {
        	if (cards.get(g).size() > 0) {
        		amtOfCardStacks++;
        	}
        }
        
    	if (isHorizontal) {
    		width = (int) ((cardWidth * amtOfCardStacks) + (cardSpacingX * (amtOfCardStacks - 1)));
    		height = (int) (cardHeight + cardSpacingY);
    		
    		if (playerNum == 0) {
    			x = (int) (frameWidth/2 - width/2);
    			y = (int) (frameHeight/2 + game.getHeight()/2 + cardSpacingY * 3);
    		} else {
    			x = (int) (frameWidth/2 - width/2);
    			y = (int) (frameHeight/2 - game.getHeight()/2 - height - cardSpacingY * 3);
    		}
    		
    		int count = 0;
    		for (Gem g : cards.keySet()) {
    			ArrayList<Card> c = cards.get(g);
    			int yOffsetCount = 0;
    			for (Card card : c) {
    				card.setWidth((int) cardWidth);
    				card.setHeight((int) cardHeight);
    				card.setX((int)(x + (cardSpacingX + cardWidth) * count));
    				card.setY((int)(y + (cardSpacingY + cardHeight/13) * yOffsetCount));
					yOffsetCount++;
    			}
				count++;
    		}
    		
    		int yOutlierOffset = 0;
    		int xOutlierCount = 0;
			for (Gem g : tokens.keySet()) {
				Card correspondingCard = null;
				
				if (cards.get(g) != null) {
					ArrayList<Card> c = cards.get(g);
					correspondingCard = c.get(c.size()-1);
				}
				
				for (Token t : tokens.get(g)) {
					t.setWidth((int) chipRadius);
					t.setHeight((int) chipRadius);
					if (correspondingCard == null) {
						t.setX((int)(x + width + cardSpacingX + (chipRadius + cardSpacingX) * (xOutlierCount/2)));
						t.setY((int)(y + (yOutlierOffset * chipSpacing * 3)));
					} else {
						t.setX((int)(correspondingCard.getX() + correspondingCard.getWidth() - chipRadius));
						t.setY((int)(correspondingCard.getY() + correspondingCard.getHeight() - chipRadius));
					}
				}
				
				if (correspondingCard == null) {
					xOutlierCount++;
					switch (yOutlierOffset) {
					case 0: yOutlierOffset = 1; break;
					case 1: yOutlierOffset = 0; break;
					default: break;
					}
				}
			}
    	} else {
    		if (amtOfCardStacks == 1) {
    			width = (int)(cardWidth + cardSpacingX);
    		} else {
    			width = (int)((cardWidth + cardSpacingX) * 2);
    		}
    		height = (int)(Math.ceil((double)amtOfCardStacks / 2) * (cardHeight + cardSpacingY));
    		
    		if (playerNum == 1) {
    			x = (int)(game.getX() - cardSpacingX - width);
    			y = (int)(game.getY() + cardSpacingY * 4);
    		} else {
    			x = (int)(game.getX() + game.getWidth() + cardSpacingX);
    			y = (int)(game.getY() + cardSpacingY * 4);
    		}
    		
    		int count = 0;
    		int yOffset = 0;
    		int maxAmtOfCards = 0;
    		int highestYValue = 0;
    		for (Gem g : cards.keySet()) {
    			ArrayList<Card> c = cards.get(g);
    			int xOffset = count % 2 == 0 ? 0 : (int)(cardSpacingX + cardWidth);
    			int cardsListYOffset = 0;
    			
    			if (tokens.containsKey(g)) {
    				for (Token t : tokens.get(g)) {
    					t.setWidth((int)chipRadius);
    					t.setHeight((int)chipRadius);
    					if (count % 2 == 0) {
    						t.setX((int)(x - chipRadius));
        					t.setY(y + yOffset);
    					} else {
    						t.setX((int)(x + cardSpacingX + cardWidth + cardWidth));
    						t.setY(y + yOffset);
    					}
    				}
    			}
    			
    			for (Card card : c) {
    				card.setWidth((int) cardWidth);
    				card.setHeight((int) cardHeight);
    				card.setX((int)(x + xOffset));
    				card.setY((int)(y + yOffset + (cardSpacingY + cardHeight/13) * cardsListYOffset));
    				if (card.getY() > highestYValue) {
    					highestYValue = card.getY();
    				}
					cardsListYOffset++;
    			}
    			if (cardsListYOffset > maxAmtOfCards) {
	    			maxAmtOfCards = cardsListYOffset;
    			}
    			if (count % 2 == 1) {
					yOffset += (int)(cardHeight + cardSpacingY + (maxAmtOfCards-1) * (cardSpacingY + cardHeight / 13));
					maxAmtOfCards = 0;
    			}
    			count++;
    		}
    		
    		int i = 0;
    		for (Gem g : tokens.keySet()) {
    			if (!cards.containsKey(g)) {
    				ArrayList<Token> t = tokens.get(g);
    				for (Token token : t) {
    					token.setWidth((int)chipRadius);
    					token.setHeight((int)chipRadius);
    					if (cards.keySet().size() == 1) {
    						token.setX((int)((i % 2) * (chipRadius + chipRadius/7) + x));
        					token.setY((int)(highestYValue + cardHeight + chipRadius/2 + ((chipRadius + chipRadius/2) * (i/2))));
    					} else {
    						token.setX((int)(i * (chipRadius + chipRadius/7) + x));
        					token.setY((int)(highestYValue + cardHeight + chipRadius/2));
    					}
    				}
    				i++;
    			}
    		}
    	}
    }

}
