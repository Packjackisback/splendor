package Splendor;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;

public class GameState {
    private Game game;
    private int frameWidth, frameHeight;
    private boolean lastTurns;
    private ArrayList<Hand> hands = new ArrayList<Hand>();
    private int currentPlayer;

    public GameState(Game game) {
        this.game = game;
        frameWidth = game.getWidth();
        frameHeight = game.getHeight();
        lastTurns = false;
        for(int i =0;i<4;i++) hands.add(new Hand(i, game));
    }
    public void nextTurn() {
        this.currentPlayer = (this.currentPlayer + 1) % 4;
    }
    public void addToCurrentPlayer(Object o) {
        if(o.getClass().getSimpleName().equals("Card")) {
            hands.get(currentPlayer).addCard((Card)o);
        }
        else if(o.getClass().getSimpleName().equals("Token")) {
            hands.get(currentPlayer).addToken((Token)o);
        }
    }
    public void makeMove(int x, int y) {

    }

    public ArrayList<Hand> getPlayerHands() {
        return hands;
    }

    public Game getGame() {return game;}

    public void updateSpecs(int fWidth, int fHeight) {}

    public void endGameCheck() {

    }

    public void setLastTurns(boolean lastTurns) {
        this.lastTurns = lastTurns;
    }
    public void drawHands(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Hand 0", hands.get(0).getX(), hands.get(0).getY());
        g.drawString("Hand 1", hands.get(1).getX(), hands.get(1).getY());
        g.drawString("Hand 2", hands.get(2).getX(), hands.get(2).getY());
        g.drawString("Hand 3", hands.get(3).getX(), hands.get(3).getY());
        for(int i = 0; i<4;i++) {
            TreeMap<Gem, ArrayList<Card>> cards = hands.get(i).getCards();
            TreeMap<Gem, ArrayList<Token>> tokens = hands.get(i).getTokens();
            for (Gem gem : cards.keySet()) {
                for (Card c : cards.get(gem)) {
                    c.draw(g);
                }
            }
            for (Gem gem : tokens.keySet()) {
                for (Token t : tokens.get(gem)) {
                    t.draw(g);
                }
            }
        }
    }
}
