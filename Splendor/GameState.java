package Splendor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class GameState {
    private static int[] score;
    private final Game game;
    private final int frameWidth;
    private final int frameHeight;
    private boolean lastTurns;
    private ArrayList<Hand> hands = new ArrayList<Hand>();
    private int currentPlayer;
    private ArrayList<Token> drawnTokens = new ArrayList<Token>();

    public static int[] getScore() {
        return score;
    }

    public GameState(Game game) {
        this.game = game;
        frameWidth = game.getWidth();
        frameHeight = game.getHeight();
        lastTurns = false;
        for (int i = 0; i < 4; i++) hands.add(new Hand(i, game));
    }

    public void nextTurn() {
        this.currentPlayer = (this.currentPlayer + 1) % 4;
    }

    public void addCardToCurrentPlayer(Card c) {
        hands.get(currentPlayer).addCard(c);
    }


    public void addTokenToCurrentPlayer(Token t, boolean overTwo) {
        if(t.getGem().getGemType().equals("Wild")) {
            if(drawnTokens.size()>0) {
                Game.showToast("Cannot take a wild after picking a card", "Alert!", "Pick again", new Runnable() {
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
    public void makeMove(int x, int y) {

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
                //TODO implement end of game
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
        g.setColor(Color.WHITE);

        for (int i = 0; i < 4; i++) { //replaces a whole chunk of logic
            g.setFont(new Font("default", currentPlayer == i ? Font.BOLD : 0, currentPlayer == i ? 20 : 16));
            g.drawString("Hand " + i, hands.get(i).getX(), hands.get(i).getY() - (i == 0 ? hands.get(i).getHeight() / 13 : 0));
        }
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
    }
}
