import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Card extends Piece {
	private Gem typeGem;
	private int worth;
	private HashMap<Gem, Integer> cost;
	private String backLevel;
	private boolean isOnBack;
	
	public Card(String type, BufferedImage img, Gem gemType, int w, HashMap<Gem, Integer> c, String backLvl) {
		super(type, img);
		typeGem = gemType;
		worth = w;
		cost = c;
		backLevel = backLvl;
		isOnBack = true;
	}
	
	public void flip() {
		isOnBack = !isOnBack;
	}
	
	// Getters
	public Gem getGem() { return typeGem; }
	public int getWorth() { return worth; }
	public HashMap<Gem, Integer> getCost() { return cost; }
}
