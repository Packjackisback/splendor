import javax.swing.*;

public class GameFrame extends JFrame {
    private int WIDTH;
    private int HEIGHT;
    public GameFrame() {
        super("Splendor");
        WIDTH = 1920;
        HEIGHT = 1080;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void setPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        pack();
    }
}
