package Splendor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Card extends Piece implements Drawable {
	private Gem typeGem;
	private int worth;
	private HashMap<Gem, Integer> cost;
	private int backLevel;
	private boolean isOnBack;

	public Card(String filename, Gem typeGem, int worth, HashMap<Gem, Integer> cost, int backLevel) {
		super("Card", Generator.loadImage(filename));
		this.typeGem = typeGem;
		this.worth =worth;
		this.cost = cost;
		this.backLevel = backLevel;
		this.isOnBack = true;
	}

	public Card() { // ONLY FOR TESTING, REMOVE WHEN DONE
		super("Card", null);
    }

	public void flip() {
		isOnBack = !isOnBack;
	}
	public static BufferedImage getBack(int backLevel) {
		switch(backLevel) {
			case 1:
				BufferedImage green = Generator.loadImage("Splendor/assets/BlueCard.jpg");
				return green;
			case 2: 
				BufferedImage yellow = Generator.loadImage("Splendor/assets/YellowCard.jpg");
				return yellow;
			case 3:
				BufferedImage blue = Generator.loadImage("Splendor/assets/GreenCard.jpg");
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
	public void draw(Graphics g) {
		BufferedImage imgToDraw = isOnBack ? getBack(backLevel) : super.getImage(); // Select front or back image
		if (imgToDraw != null) {
			g.drawImage(imgToDraw, getX(), getY(), getWidth(), getHeight(), null);
		}
	}
}
