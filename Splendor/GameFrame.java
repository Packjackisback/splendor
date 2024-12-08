package Splendor;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    public GameFrame() {
        super("Splendor");
        WIDTH = 1920;
        HEIGHT = 1080;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    public void setPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        pack();
    }

    public void setPanel(EndPanel panel, int[] scores, ArrayList<Hand> hands) {
    	panel.updateScores(scores, hands);
        getContentPane().removeAll();
        getContentPane().add(panel);
        pack();
    }
    public void addMouseListener(MouseListener l) {
        getContentPane().addMouseListener(l);
    }
}