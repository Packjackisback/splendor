package Splendor;
import java.awt.event.MouseEvent;
import java.util.*;
public class MouseListener implements java.awt.event.MouseListener {
    public Game game;
    public GameState gameState;
    private Set<Token> tokenKeys;
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
        for (Token token : tokenKeys) {
            if (x >= token.getX() && x <= token.getX() + token.getWidth() && y >= token.getY() && y <= token.getY() + token.getHeight()) {
                game.takeToken(token);
                gameState.addToCurrentPlayer(token);
                break;

            }
        }

        for (ArrayList<Card> arr: cards) {
            for (Card card : arr) {
                if (x >= card.getX() && x <= card.getX() + card.getWidth() && y >= card.getY() && y <= card.getY() + card.getHeight()) {
                    game.takeCard(card);
                    card.flip();
                    gameState.addToCurrentPlayer(card);
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
