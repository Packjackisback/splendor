import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        super("Splendor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void setPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        pack();
    }
}
