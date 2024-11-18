package Splendor;
import javax.accessibility.AccessibleAction;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;
public class mouseListener implements MouseListener {
    public Game game;
    private Set<Token> tokenKeys;
    private ArrayList<Stack<Card>> cardStacks;
    private ArrayList<ArrayList<Card>> cardRows;
    private ArrayList<Noble> nobles;
    public mouseListener(Game game) {
        this.game = game;
        this.tokenKeys = game.getTokens().keySet();
        this.cardStacks = game.getDrawCards();
        this.cardRows = game.getCardArray();
        this.nobles = game.getNobles();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (Token token : tokenKeys) {
            if (x >= token.getX() && x <= token.getX() + token.getWidth() && y >= token.getY() && y <= token.getY() + token.getHeight()) {
                game.takeToken(token);
                System.out.println("Drawing token");
                break;
            }
        }
        for(Stack s : cardStacks) {
            Card top = (Card) s.peek();
            if(x>=top.getX() && x<=top.getX() + top.getWidth() && y>=top.getY() && y<=top.getY() + top.getHeight()) {
               //game.pickCard(top);
               System.out.println("Picking card");
               break;
            }
        }
        for(ArrayList<Card> a : cardRows) {
            for(Card c : a) {
                if(x>=c.getX() && x<=c.getX() + c.getWidth() && y>=c.getY() && y<=c.getY() + c.getHeight()) {
                    game.takeCard(c);
                    System.out.println("Taking card");
                    break;
                }
            }
        }
        for(Noble n : nobles) {
            if(x>=n.getX() && x<n.getX() + n.getWidth() && y>=n.getY() && y<=n.getY() + n.getHeight()) {
                game.takeNoble(n);
                System.out.println("Taking noble");
                break;
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
