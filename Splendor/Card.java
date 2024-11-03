package Splendor;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Card extends Piece implements Drawable{
	private Gem typeGem;
	private int worth;
	private HashMap<Gem, Integer> cost;
	private int backLevel;
	private boolean isOnBack;

	public Card(BufferedImage img, Gem typeGem, int worth, HashMap<Gem, Integer> cost, int backLevel) {
		super("Card", img);
		this.typeGem = typeGem;
		this.worth =worth;
		this.cost = cost;
		this.backLevel = backLevel;
		this.isOnBack = true;
	}

	public void flip() {
		isOnBack = !isOnBack;
	}
	public static BufferedImage getBack(int backLevel) {
		switch(backLevel) {
			case 1:
				BufferedImage green = ImageLoader.loadImage("Splendor/assets/GreenCard.jpg");
				return green;
			case 2: 
				BufferedImage yellow = ImageLoader.loadImage("Splendor/assets/YellowCard.jpg");
				return yellow;
			case 3:
				BufferedImage blue = ImageLoader.loadImage("Splendorassets/BlueCard.jpg");
				return blue;
			default:
				throw new RuntimeException("BackLevel not found\nBackLevel: " + backLevel);
        }
	}
	
	// Getters
	public Gem getGem() { return typeGem; }
	public int getWorth() { return worth; }
	public HashMap<Gem, Integer> getCost() { return cost; }

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		BufferedImage imgToDraw = isOnBack ? getBack(backLevel) : super.getImage(); // Select front or back image
		if (imgToDraw != null) {
			g.drawImage(imgToDraw, x, y, width, height, null);
		}
	}
}
