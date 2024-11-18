package Splendor;
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
    public mouseListener(Game game) {
        this.game = game;
        tokenKeys = game.getTokens().keySet();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (Token token : tokenKeys) {
            if (x >= token.getX() && x <= token.getX() + token.getWidth() && y >= token.getY() && y <= token.getY() + token.getHeight()) {
                game.takeToken(token);
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
