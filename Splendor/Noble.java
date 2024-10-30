import java.awt.image.BufferedImage;

public class Noble extends Piece {
	private int worth;
	
	public Noble(String type, BufferedImage img, int w) {
		super(type, img);
		worth = w;
	}
	
	public int getWorth() { return worth; }
}
