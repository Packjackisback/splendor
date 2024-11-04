package Splendor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Noble extends Piece implements Drawable{
	private int worth;
	private HashMap<Gem, Integer> cost;

	
	public Noble(BufferedImage img, HashMap<Gem, Integer> cost) {
		super("Noble", img);
		this.cost = cost;
		this.worth = 3;
	}
	
	public int getWorth() { return worth; }

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.drawImage(super.getImage(), x, y, width, height, null);
	}
}
