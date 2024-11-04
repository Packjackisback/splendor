package Splendor;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Noble extends Piece implements Drawable{
	private int worth;

	
	public Noble(BufferedImage img) {
		super("Noble", img);
		worth = 3;
	}
	
	public int getWorth() { return worth; }

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.drawImage(super.getImage(), x, y, width, height, null);
	}
}
