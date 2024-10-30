import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

import javax.swing.JPanel;

public abstract class Piece {
	private int x,y;
	private int width, height;
	private String type;
	private BufferedImage image;
	
	// Animation variables
	
	private boolean inAnimation;
	private double propOldX, propOldY, propNewX, propNewY, propXDist, propYDist;
	private int time, elapsedTime;
	private Timer animationTimer;
	
	public Piece(String t, BufferedImage img) {
		type = t;
		image = img;
	}
	
	// Getters
	public int getX() { return x; }
	public int getY() { return y; }
	public BufferedImage getImage() { return image; }
	public String getType() { return type; }
	public boolean isInAnimation() { return inAnimation; }
	
	
	// Setters
	public void setX(int newX) { x = newX; }
	public void setY(int newY) { y = newY; }
	public void setWidth(int w) { width = w; }
	public void setHeight(int h) { height = h; }
	
	// Animation methods
	public void startAnimation(int newX, int newY, int t, JPanel gamePanel) {
		int frameWidth = gamePanel.getWidth();
		int frameHeight = gamePanel.getHeight();
		inAnimation = true;
		propOldX = (double) x / frameWidth;
		propOldY = (double) y / frameHeight;
		propNewX = (double) newX / frameWidth;
		propNewY = (double) newY / frameHeight;
		time = t;
		elapsedTime = 0;

		// Distance to move
		propXDist = propNewX - propOldX;
		propYDist = propNewY - propOldY;

		// Animation Timer: every 16ms (around 60 FPS)
		animationTimer = new Timer(16, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAnimationVals(gamePanel);
			}
		});
		animationTimer.start();
	}

	private void updateAnimationVals(JPanel gamePanel) {
		int frameWidth = gamePanel.getWidth();
		int frameHeight = gamePanel.getHeight();
		elapsedTime += 16;
		double progress = (double) elapsedTime / time;

		if (progress >= 1.0) {
			progress = 1.0;
			inAnimation = false;
			animationTimer.stop();
		}

		// Update the current x, y based on the progress
		x = (int) (frameWidth * (propOldX + propXDist * progress));
		y = (int) (frameHeight * (propOldY + propYDist * progress));

		// Request the panel to repaint the object in the new position
		gamePanel.repaint();
	}
}
