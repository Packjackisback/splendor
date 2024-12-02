package Splendor;
import java.awt.event.MouseEvent;
import java.util.*;
public class MouseListener implements java.awt.event.MouseListener {
    public Game game;
    private GameState gameState;
    private Set<Gem> tokenKeys;
    private ArrayList<ArrayList<Card>> cards;
    public MouseListener(Game game, GameState gameState) {
        this.game = game;
        tokenKeys = game.getTokens().keySet();
        cards = game.getCardArray();
        this.gameState = gameState;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (Gem gem : tokenKeys) {
        	for (Token token : game.getTokens().get(gem)) {
        		if (x >= token.getX() && x <= token.getX() + token.getWidth() && y >= token.getY() && y <= token.getY() + token.getHeight()) {
                    gameState.addTokenToCurrentPlayer(token, game.getTokens().get(gem).size()>2);
                    cards = game.getCardArray();
                    break;
                }
        	}
        }

        for (ArrayList<Card> arr: cards) {
            for (Card card : arr) {
                if (x >= card.getX() && x <= card.getX() + card.getWidth() && y >= card.getY() && y <= card.getY() + card.getHeight()) {
                    if(gameState.getCurrentPlayerHand().canAfford(card)) {
                        game.takeCard(card);
                        card.flip();
                        gameState.addCardToCurrentPlayer(card);
                        gameState.nextTurn();
                    }
                    else {
                        Runnable okay = new Runnable() {
                            public void run() {
                                System.out.println("Okay");
                            }
                        };
                        Game.showToast("Cannot afford card", "Error!", "Ok", okay);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
