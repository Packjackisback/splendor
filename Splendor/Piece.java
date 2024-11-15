package Splendor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.Timer;

import javax.swing.JPanel;

public abstract class Piece {
	private int x,y;
	private int width, height;
	private final String type;
	private final BufferedImage image;
	
	// Animation variables
	
	private boolean inAnimation;
	private HashMap<String, Double> animationValues;
	
	public Piece(String t, BufferedImage img) {
		type = t;
		image = img;
	}
	
	// Getters
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public BufferedImage getImage() { return image; }
	public String getType() { return type; }
	public boolean isInAnimation() { return inAnimation; }
	public HashMap<String, Double> getAnimationValues() { return animationValues; }
	
	// Setters
	public void setX(int newX) { x = newX; }
	public void setY(int newY) { y = newY; }
	public void setWidth(int w) { width = w; }
	public void setHeight(int h) { height = h; }
	public void setIsInAnimation(boolean is) { inAnimation = is; }
	
	// Animation methods
	public void startAnimation(int newX, int newY, JPanel gamePanel) {
		animationValues = new HashMap<String, Double>();
		int frameWidth = gamePanel.getWidth();
		int frameHeight = gamePanel.getHeight();
		inAnimation = true;
		animationValues.put("propOldX", (double) x / frameWidth);
		animationValues.put("propOldY", (double) y / frameHeight);
		animationValues.put("propNewX", (double) newX / frameWidth);
		animationValues.put("propNewY", (double) newY / frameHeight);

		// Distance to move
		double propXDist = animationValues.get("propNewX") - animationValues.get("propOldX");
		double propYDist = animationValues.get("propNewY") - animationValues.get("propOldY");
		animationValues.put("propXDist", propXDist);
		animationValues.put("propYDist", propYDist);
	}
}
