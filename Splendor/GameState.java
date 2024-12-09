package Splendor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

public class GameState {
    private static int[] score;
    private Game game;
    private final int frameWidth;
    private final int frameHeight;
    private boolean lastTurns;
    private int playerLastTurnIndex;
    public static ArrayList<Hand> hands = new ArrayList<Hand>();
    private int currentPlayer;
    private ArrayList<Token> drawnTokens = new ArrayList<Token>();
    private EndPanel endPanel;
    private GameFrame gameFrame;

	public static TreeMap<Integer, Integer> getScore() { // Returns playerNum, score
		TreeMap<Integer, Integer> scoreMap = new TreeMap<Integer, Integer>();
		for (int i = 0; i < hands.size(); i++) {
			scoreMap.put(i + 1, hands.get(i).getScore());
		}
		return scoreMap;
	}

    public GameState(Game game, EndPanel endPanel, GameFrame gameFrame) {
        this.game = game;
        this.endPanel = endPanel;
        this.gameFrame = gameFrame;
        frameWidth = game.getWidth();
        frameHeight = game.getHeight();
        lastTurns = false;
        for (int i = 0; i < 4; i++) hands.add(new Hand(i, game));
        score = new int[4];
        
		//hands.get(0).addCard(new Card("/Splendor/assets/Cards/01.jpg", new Gem("White"), 15, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/01.jpg", new Gem("White"), 14, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/01.jpg", new Gem("White"), 1, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/01.jpg", new Gem("White"), 15, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/01.jpg", new Gem("White"), 7, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/01.jpg", new Gem("White"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/01.jpg", new Gem("White"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/019.jpg", new Gem("Red"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/019.jpg", new Gem("Red"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/019.jpg", new Gem("Red"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/019.jpg", new Gem("Red"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/012.jpg", new Gem("Blue"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/012.jpg", new Gem("Blue"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/012.jpg", new Gem("Blue"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/012.jpg", new Gem("Blue"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/013.jpg", new Gem("Green"), 6, new HashMap<Gem, Integer>(), 0));
        //hands.get(0).addCard(new Card("/Splendor/assets/Cards/013.jpg", new Gem("Green"), 6, new HashMap<Gem, Integer>(), 0));
        
        
        //for (int i = 0; i < 3; i++) {
        //	hands.get(0).addToken(new Token(new Gem("Red")));
        //	hands.get(0).addToken(new Token(new Gem("Blue")));
        //	hands.get(0).addToken(new Token(new Gem("Green")));
        //}
        //hands.get(0).addToken(new Token(new Gem("White")));
    }

	public void nextTurn() {
		System.out.println("Next turn called.");
		TreeMap<Gem, ArrayList<Card>> playerCards = hands.get(currentPlayer).getCards();
		boolean isNobleChosen = false;
		Noble nobleChosen = null;
		for (Noble n : game.getNobles()) {
			if (!isNobleChosen) {
				boolean canAfford = true;
				HashMap<Gem, Integer> nobleCost = n.getCost();

				for (Gem g : nobleCost.keySet()) {
					if (!playerCards.containsKey(g) || nobleCost.get(g) > playerCards.get(g).size()) {
						canAfford = false;
					}
				}

				if (canAfford) {
					System.out.println("Noble " + n + " can afford");
					nobleChosen = n;
					isNobleChosen = true;
				} else {
					System.out.println("Noble " + n + " can't afford");
				}
			}
		}
		
		if (nobleChosen != null) {
			game.takeNoble(nobleChosen);
			hands.get(currentPlayer).addNoble(nobleChosen);
		}
		
		if (this.lastTurns == false) {
			endGameCheck();
		}
		
		this.currentPlayer = (this.currentPlayer + 1) % 4;
		
		if (this.lastTurns && currentPlayer == playerLastTurnIndex) {
			invokeEnd();
		}
		
		drawnTokens = new ArrayList<Token>();
	}

    public void addCardToCurrentPlayer(Card c, HashMap<Gem, Integer> tokensToRemove) {  //TODO remove tokens from current player as needed
    	System.out.println("Adding card, tokens to remove: " + tokensToRemove);
        TreeMap<Gem, ArrayList<Card>> current = getCurrentPlayerHand().getCards();
        for(Gem g : tokensToRemove.keySet()) {
          //First, we need to find the gem cost - the discount
          int tokenToRemove = tokensToRemove.get(g);
          
          while(tokenToRemove>0) {
            game.addToken(new Token(g));
            getCurrentPlayerHand().removeToken(new Token(g));
            tokenToRemove--;
          }
        }
        hands.get(currentPlayer).addCard(c);
        
        game.getPanel().repaint();
    }


    public void addTokenToCurrentPlayer(Token t, boolean overTwo) {
        if(t.getGem().getGemType().equals("Wild")) {
            if(drawnTokens.size()>0) {
                Game.showToast("Cannot take a wild after picking a token", "Alert!", "Pick again", new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Picking again");
                    }
                });
                return;
            }
            game.takeToken(t);
            hands.get(currentPlayer).addToken(t);
            checkCurrentPlayerTokenCount();
            //TODO implement reserving cards
            nextTurn();
            return;
        }
        
        for(Token p : drawnTokens) {
            if(t.getGem().getGemType().equals(p.getGem().getGemType())) {
                System.out.println("Already contains said token");
                if (drawnTokens.size() > 1) {
                    Game.showToast("Cannot take 2 of the same tokens", "Alert!", "Pick again", new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Picking again");
                        }
                    });
                    return;
                }
                if(!overTwo) {
                    Game.showToast("Not enough tokens in the stack", "Alert!", "Pick again", new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Picking again");
                        }
                    });
                    return;
                }
                game.takeToken(t);
                hands.get(currentPlayer).addToken(t);
                checkCurrentPlayerTokenCount();
                nextTurn();
                return;
            }
        }
        game.takeToken(t);
        hands.get(currentPlayer).addToken(t);
        drawnTokens.add(t);
        checkCurrentPlayerTokenCount();
        if (drawnTokens.size() >= 3) {
            nextTurn();
        }
    }

    public void checkCurrentPlayerTokenCount() {
        int totalSize = 0;
        for(ArrayList<Token> at : hands.get(currentPlayer).getTokens().values())
            totalSize += at.size();
        System.out.println(totalSize);
        if(totalSize > 10) game.showDialog(displayTokenLimitDialog());
    }
    
	private JDialog displayTokenLimitDialog() {
		System.out.println("Too many tokens! Calling dialog.");
		int currentPlayerHold = currentPlayer;
		TreeMap<Gem, ArrayList<Token>> tokenMap = hands.get(currentPlayerHold).getTokens();

		JDialog dialog = new JDialog();
		dialog.setTitle("Token Limit Exceeded");

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(tokenMap.size(), 2));

		ButtonGroup buttonGroup = new ButtonGroup();

		for (Map.Entry<Gem, ArrayList<Token>> entry : tokenMap.entrySet()) {
			JCheckBox checkbox = new JCheckBox(entry.getKey().getGemType());
			panel.add(checkbox);
			buttonGroup.add(checkbox);
		}

		JButton okButton = new JButton("OK");
		okButton.addActionListener(e -> {
			for (Component component : panel.getComponents()) {
				if (component instanceof JCheckBox && ((JCheckBox) component).isSelected()) {
					String selectedGemType = ((JCheckBox) component).getText();
					for (Map.Entry<Gem, ArrayList<Token>> entry : tokenMap.entrySet()) {
						if (entry.getKey().getGemType().equals(selectedGemType)) {
							ArrayList<Token> tokens = entry.getValue();
							if (!tokens.isEmpty()) {
								Token tokenToRemove = tokens.get(0);
								System.out.println("Current player: " + currentPlayerHold);
								hands.get(currentPlayerHold).removeToken(tokenToRemove);
								returnTokens(tokenToRemove);
							}
							break;
						}
					}
					break;
				}
			}
			dialog.dispose();
		});

		dialog.add(panel, BorderLayout.CENTER);
		dialog.add(okButton, BorderLayout.SOUTH);
		dialog.setLocationRelativeTo(null);
		dialog.pack();
		dialog.setVisible(true);
		return dialog;
	}

    public void returnTokens(Token t) {
        game.addToken(t);
    }

    public ArrayList<Hand> getPlayerHands() {
        return hands;
    }

    public Game getGame() {
        return game;
    }

    public void updateSpecs(int fWidth, int fHeight) {
    }

	public void endGameCheck() {
		Hand h = hands.get(currentPlayer);
		System.out.println("Hand score: " + h.getScore());
		if (h.getScore() >= 15) {
			setLastTurns(true);
			playerLastTurnIndex = currentPlayer;
		}

	}

    public Hand getCurrentPlayerHand() {
        return hands.get(currentPlayer);
    }

    public void setLastTurns(boolean lastTurns) {
        this.lastTurns = lastTurns;
    }

    public void drawHands(Graphics g) {
        //g.setColor(Color.WHITE);
    	g.setColor(Color.YELLOW);
    	    	
    	g.setFont(new Font("default", currentPlayer == 0 ? Font.BOLD : 0, currentPlayer == 0 ? 20 : 16));
        g.drawString("Player 1", hands.get(0).getX(), hands.get(0).getY() - hands.get(0).getHeight() / 13 + 15);
        
    	g.setFont(new Font("default", currentPlayer == 1 ? Font.BOLD : 0, currentPlayer == 1 ? 20 : 16));
        g.drawString("Player 2", 20, gameFrame.getHeight() / 2);
        
    	g.setFont(new Font("default", currentPlayer == 2 ? Font.BOLD : 0, currentPlayer == 2 ? 20 : 16));
        g.drawString("Player 3", hands.get(2).getX(), 35);
        
    	g.setFont(new Font("default", currentPlayer == 3 ? Font.BOLD : 0, currentPlayer == 3 ? 20 : 16));
        g.drawString("Player 4", gameFrame.getWidth() - 100, gameFrame.getHeight() / 2);
        /*for (int i = 0; i < 4; i++) { //replaces a whole chunk of logic. Basically, the first one sets the font to bold for the current player, the second draws the position.
            g.setFont(new Font("default", currentPlayer == i ? Font.BOLD : 0, currentPlayer == i ? 20 : 16));
            g.drawString("Hand: " + i, hands.get(i).getX(), hands.get(i).getY() - (i == 0 ? hands.get(i).getHeight() / 13 : 0));
        }*/
        for (int i = 0; i < 4; i++) {
            TreeMap<Gem, ArrayList<Card>> cards = hands.get(i).getCards();
            TreeMap<Gem, ArrayList<Token>> tokens = hands.get(i).getTokens();
            for (Gem gem : cards.keySet()) {
                for (Card c : cards.get(gem)) {
                    c.draw(g);
                }
            }
            for (Gem gem : tokens.keySet()) {
            	boolean isOnBottom = cards.containsKey(gem);
            	if (i == 1 || i == 3) {
            		isOnBottom=false;
            	}
                for (Token t : tokens.get(gem)) {
                    t.draw(g, tokens.get(gem).size(), isOnBottom);
                }
            }
        }
        for (Hand h : hands) {
        	for (Noble n : h.getNobles()) {
        		n.draw(g);
        	}
        	
        	for (Card c : h.getReservedCards()) {
        		c.draw(g);
        	}
        	
        	for (Token t : h.getReservedTokens()) {
        		if (t != null) {
        			t.draw(g, 0, false);
        		}
        	}
        }
    }

    public void invokeEnd() {
    	for (int i = 0; i < 4; i++) {
	    	score[i] = hands.get(i).getScore();
    	}
        gameFrame.setPanel(endPanel, score, hands);
        System.out.println("Escaped");
    }
    
    public ArrayList<Token> getDrawnTokens() {
    	return drawnTokens;
    }
}
