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
    private static ArrayList<Hand> hands = new ArrayList<Hand>();
    private int currentPlayer;
    private ArrayList<Token> drawnTokens = new ArrayList<Token>();
    private EndPanel endPanel;
    private GameFrame gameFrame;

    public static int[] getScore() {
        for (int i = 0; i < hands.size(); i++) {
            score[i] = hands.get(i).getScore();
        }
        System.out.println(Arrays.toString(score));
        return score;
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
    }

    public void nextTurn() {
        this.currentPlayer = (this.currentPlayer + 1) % 4;
    }

    public void addCardToCurrentPlayer(Card c) {  //TODO remove tokens from current player as needed
        HashMap<Gem, Integer> cost = c.getCost();
        TreeMap<Gem, ArrayList<Card>> current = getCurrentPlayerHand().getCards();
        for(Gem g : cost.keySet()) {
          //First, we need to find the gem cost - the discount
          int tokensToRemove = cost.get(g);
          tokensToRemove -= current.containsKey(g) ? current.get(g).size() : 0;
          if(tokensToRemove>getCurrentPlayerHand().getTokens().get(g).size()) throw new RuntimeException("Not enough tokens to remove somehow, check line 51ish");
          while(tokensToRemove>0) {
            
            game.addToken(new Token(g));
            getCurrentPlayerHand().removeToken(new Token(g));
            tokensToRemove--;
          }
        }
        hands.get(currentPlayer).addCard(c);
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
                    Game.showToast("Cannot take 2 tokens when you have already taken one", "Alert!", "Pick again", new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Picking again");
                        }
                    });
                    return;
                }
                if(!overTwo) {
                    Game.showToast("Not enough tokens", "Alert!", "Pick again", new Runnable() {
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
                drawnTokens = new ArrayList<Token>();
                return;
            }
        }
        game.takeToken(t);
        hands.get(currentPlayer).addToken(t);
        drawnTokens.add(t);
        checkCurrentPlayerTokenCount();
        if (drawnTokens.size() >= 3) {

            nextTurn();
            drawnTokens = new ArrayList<Token>();
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
        System.out.println("too many tokens! Calling dialog");
        TreeMap<Gem, ArrayList<Token>> tokenMap = hands.get(currentPlayer).getTokens();
        JDialog dialog = new JDialog();
        dialog.setTitle("Token Limit Exceeded");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(tokenMap.size(), 2));

        // Create checkboxes for each token type
        for (Map.Entry<Gem, ArrayList<Token>> entry : tokenMap.entrySet()) {
            JCheckBox checkbox = new JCheckBox(entry.getKey().getGemType());
            panel.add(checkbox);
        }

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            // Get selected token types and discard one token for each selected type
            for (Component component : panel.getComponents()) {
                if (component instanceof JCheckBox && ((JCheckBox) component).isSelected()) {
                    String selectedGemType = ((JCheckBox) component).getText();
                    for (Map.Entry<Gem, ArrayList<Token>> entry : tokenMap.entrySet()) {
                        if (entry.getKey().getGemType().equals(selectedGemType)) {
                            ArrayList<Token> tokens = entry.getValue();
                            if (!tokens.isEmpty()) {
                                Token tokenToRemove = tokens.remove(0); // Remove the first token
                                returnTokens(tokenToRemove); // Return the token to the game
                                break; // Found the matching gem type, no need to continue the loop
                            }
                        }
                    }
                }
            }
            dialog.dispose();
        });

        panel.add(okButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        return dialog;
    }
    public void returnTokens(Token t) {
        hands.get(currentPlayer).removeToken(t);
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
        for(int i : score) {
            if(i>15) {
              invokeEnd();
            }
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
    	    	
    	g.drawString("Hand " + (i+1) + " Score: " + hands.get(i).getScore() , ((i==0||i==2) ? gameFrame.getWidth()/ 2 - 10 : hands.get(i).getX()) - (i==1 ? gameFrame.getWidth() / 5 - 25 : 0) + (i==3 ? gameFrame.getWidth() / 5 - 25:0), ((i==3||i==1) ? gameFrame.getHeight()/2 : hands.get(i).getY()) - (i == 0 ? hands.get(i).getHeight() / 13 : 0));
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
                for (Token t : tokens.get(gem)) {
                    t.draw(g, tokens.get(gem).size());
                }
            }
        }
        for (Hand h : hands) {
        	for (Noble n : h.getNobles()) {
        		n.draw(g);
        	}
        }
    }

    public void invokeEnd() {
        gameFrame.setPanel(endPanel, score);
    }
}
