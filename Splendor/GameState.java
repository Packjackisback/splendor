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

public class GameState {
    private Game game;
    private int frameWidth, frameHeight;
    private boolean lastTurns;

    public GameState(Game game) {
        this.game = game;
        frameWidth = game.getWidth();
        frameHeight = game.getHeight();
        lastTurns = false;
    }

    public void makeMove(int x, int y) {

    }

    public ArrayList<Hand> getPlayerHands() {
        return null;
    }

    public Game getGame() {return game;}

    public void updateSpecs(int fWidth, int fHeight) {}

    public void endGameCheck() {

    }

    public void setLastTurns(boolean lastTurns) {
        this.lastTurns = lastTurns;
    }
}
