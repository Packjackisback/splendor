package Splendor;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class StartPanel extends JPanel implements MouseListener {
    private BufferedImage background1, background2;
    private GameFrame frame;
    private boolean fading = false;
    private Timer fadeTimer;
    private float alpha = 0.5f;
    private boolean isBackground1 = true;
    private GamePanel nextPanel;
    private Timer delayTimer;

    public StartPanel(GameFrame frame, GamePanel nextPanel) {
        this.frame = frame;
        this.nextPanel = nextPanel;
        setPreferredSize(new Dimension(1920, 1080));
        try {
            background1 = Generator.loadImage("Splendor/assets/startBackground.PNG");
            background2 = Generator.loadImage("Splendor/assets/blackImage.jpg");
        } catch (Exception E) {
            System.out.println("Exception Error");
        }

        fadeTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fading) {
                    alpha += 0.02f;
                    if (alpha >= 1.0f) {
                        alpha = 1.0f;
                        fading = false;
                        isBackground1 = !isBackground1;
                        delayTimer.start();
                    }

                }
                repaint();
            }
        });
        delayTimer = new Timer(1000, new ActionListener() { // 1000 milliseconds = 1 second
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your method here
                frame.setPanel(nextPanel);
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        Composite originalComposite = g2d.getComposite();
        if(alpha>0.7)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(isBackground1 ? background1 : background2, 0, 0, getWidth(), getHeight(), null);
        g2d.setComposite(originalComposite);
        g2d.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        alpha = 0.7f;
        fading=true;
        fadeTimer.start();
        System.out.println("Mouse Down");
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
