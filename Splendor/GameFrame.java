package Splendor;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private boolean isEnded;
    
    public GameFrame() {
        super("Splendor");
        WIDTH = 1920;
        HEIGHT = 1080;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        isEnded = false;
    }

    public void setPanel(JPanel panel) {
    	if (!isEnded) {
	    	System.out.println("Panel Changed");
	        getContentPane().removeAll();
	        getContentPane().add(panel);
	        pack();
    	}
    }

    public void setPanel(EndPanel panel, int[] scores, ArrayList<Hand> hands) {
    	isEnded = true;
    	panel.updateScores(scores, hands);
    	getContentPane().removeAll();
        System.out.println("'Removed' the panel");
        getContentPane().add(panel);
        revalidate();
        repaint();
    }
    public void addMouseListener(MouseListener l) {
        getContentPane().addMouseListener(l);
    }
}