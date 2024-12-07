package Splendor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Noble extends Piece implements Drawable{
	private final int worth;
	private final HashMap<Gem, Integer> cost;
	private String nobleImgString;
	
	public Noble(String img, HashMap<Gem, Integer> cost) {
		super("Noble", Generator.loadImage(img));
		this.cost = cost;
		this.worth = 3;
		nobleImgString = img;
	}
	public HashMap<Gem, Integer> getCost() {
		return cost;
	}
	public int getWorth() { return worth; }
	public String getNobleImgString() { return nobleImgString; }
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(super.getImage(), getX(), getY(), getWidth(), getHeight(), null);
	}
}