package Splendor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Noble extends Piece implements Drawable{
	private final int worth;
	private final HashMap<Gem, Integer> cost;

	
	public Noble(String img, HashMap<Gem, Integer> cost) {
		super("Noble", Generator.loadImage(img));
		this.cost = cost;
		this.worth = 3;
	}
	
	public int getWorth() { return worth; }

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.drawImage(super.getImage(), x, y, width, height, null);
	}
}
