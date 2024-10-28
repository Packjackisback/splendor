import java.awt.image.BufferedImage;

public class Token extends Piece {
	private Gem gem;
	
	public Token(String type, BufferedImage img, Gem g) {
		super(type, img);
		gem = g;
	}
}
