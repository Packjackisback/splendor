package Splendor;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StartPanel extends JPanel{
    private BufferedImage background;
    public StartPanel() {
        setPreferredSize(new Dimension(1920, 1080));
        try {
            background = ImageIO.read(StartPanel.class.getResource("/Splendor/assets/start background.PNG"));
        } catch (Exception E) {
            System.out.println("Exception Error");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }
}
